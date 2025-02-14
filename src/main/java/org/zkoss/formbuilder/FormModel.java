package org.zkoss.formbuilder;

import java.io.*;
import java.net.URL;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Div;
import org.zkoss.zul.TreeNode;

/**
 * store a form structure in a tree
 */
public class FormModel extends AbstractTreeModel<FormNode> {

    private Template formTemplate;
    private VelocityEngine velocityEngine;

    /* simplify creating a instance, create a default root node */
    public FormModel(){
        super(new FormNode(null, new LinkedList<>()));
        initTemplateEngine();
        loadDefaultFieldTemplate();
    }

    public FormModel(FormNode root) {
        super(root);
        initTemplateEngine();
        loadDefaultFieldTemplate();
    }

    public static final String TEMPLATE_PATH = "template/";
    /* the template of various fields. zul file name as its key, e.g. shortText.zul -> shortText */
    private Map<String, Template> fieldTemplates = new HashMap<>();

    private void loadDefaultFieldTemplate() {
        List<String> zulFiles = getZulFiles(TEMPLATE_PATH);
        for (String zulFile : zulFiles){
            String[] fileParts = zulFile.replace(TEMPLATE_PATH, "").split("\\.");
            fieldTemplates.put(fileParts[0], velocityEngine.getTemplate(zulFile));
        }
    }

    static private List<String> getZulFiles(String templatePath) {
        List<String> zulFiles = new ArrayList<>();
        Enumeration<URL> resources = null;
        try {
            resources = FormModel.class.getClassLoader().getResources(templatePath);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                File folder = new File(resource.getFile());
                File[] files = folder.listFiles((dir, name) -> name.endsWith(".zul"));
                if (files != null) {
                    for (File file : files) {
                        zulFiles.add(templatePath + file.getName());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return zulFiles;
    }

    @Override
    public boolean isLeaf(FormNode node) {
        return node.isLeaf();
    }


    @Override
    public FormNode getChild(FormNode parent, int index) {
        return parent.getChildAt(index);
    }

    @Override
    public int getChildCount(FormNode parent) {
        return parent.getChildCount();
    }

    /**
     * Generate the form into zul content.
     */
    public String toZul() {
        List<String> rowsContent = new LinkedList();
        for (TreeNode<FormField> node : this.getRoot().getChildren()) {
            nodeToRows(node, rowsContent);
        }
        VelocityContext templateContext = new VelocityContext();
        templateContext.put("rowsContent", rowsContent);
        return produceZulContent(templateContext);
    }

    private String produceZulContent(VelocityContext templateContext) {
        StringWriter writer = new StringWriter();
        formTemplate.merge(templateContext, writer);
        return writer.toString();
    }

    private void initTemplateEngine() {
        //setup to load template from classpath
        Properties properties = new Properties();
        properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
        formTemplate = velocityEngine.getTemplate("formTemplate.zul");
    }

    private void nodeToRows(TreeNode<FormField> node, List<String> rowsContent) {
        rowsContent.add(renderNodeTemplate(node));
        for (TreeNode<FormField> childNode : node.getChildren()) {
            nodeToRows(childNode, rowsContent);
        }
    }

    private String renderNodeTemplate(TreeNode<FormField> node) {
        FormField field = node.getData();
        Template template = fieldTemplates.get(field.getType());
        if (template == null) {
            return "";
        }
        VelocityContext templateContext = new VelocityContext();
        templateContext.put("name", field.getName());
        templateContext.put("value", Optional.ofNullable(field.getValue()).orElse(""));
        StringWriter writer = new StringWriter();
        template.merge(templateContext,writer);

        return writer.toString();
    }

    /**
     * Generate the form into a set of {@link Component}.
     * @return root component of the form
     */
    public Component toComponents(FormbuilderNodeRenderer renderer) {
        return renderNode(renderer, this.getRoot());
    }

    private Component renderNode(FormbuilderNodeRenderer renderer, FormNode rootNode) {
        Div rootComponent = new Div();
        rootComponent.addSclass("formItem");
        if (rootNode.getData() != null) {
            for (Component renderedChild : Arrays.asList(renderer.render(rootNode.getData()))) {
                rootComponent.appendChild(renderedChild);
            }
        }
        Div childrenDiv = new Div();
        childrenDiv.setSclass("formItemChildren");
        rootComponent.appendChild(childrenDiv);
        for (TreeNode<FormField> childNode : rootNode.getChildren()) {
            childrenDiv.appendChild(renderNode(renderer, (FormNode) childNode));
        }
        return rootComponent;
    }


}
