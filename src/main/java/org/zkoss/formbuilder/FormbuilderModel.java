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
public class FormbuilderModel extends AbstractTreeModel<FormbuilderNode> {

    private Template formTemplate;
    private VelocityEngine velocityEngine;

    public FormbuilderModel(FormbuilderNode root) {
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
            resources = FormbuilderModel.class.getClassLoader().getResources(templatePath);

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
    public boolean isLeaf(FormbuilderNode node) {
        return node.isLeaf();
    }


    @Override
    public FormbuilderNode getChild(FormbuilderNode parent, int index) {
        return parent.getChildAt(index);
    }

    @Override
    public int getChildCount(FormbuilderNode parent) {
        return parent.getChildCount();
    }

    public String toZul() {
        List<String> rowsContent = new LinkedList();
        for (TreeNode<FormbuilderItem> node : this.getRoot().getChildren()) {
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

    private void nodeToRows(TreeNode<FormbuilderItem> node, List<String> rowsContent) {
        rowsContent.add(renderNodeTemplate(node));
        for (TreeNode<FormbuilderItem> childNode : node.getChildren()) {
            nodeToRows(childNode, rowsContent);
        }
    }

    private String renderNodeTemplate(TreeNode<FormbuilderItem> node) {
        FormbuilderItem field = node.getData();
        Template template = fieldTemplates.get(field.getType());
        if (template == null) {
            return "";
        }
        VelocityContext templateContext = new VelocityContext();
        templateContext.put("nodeName", field.getName());
        templateContext.put("nodeValue", field.getValue());
        StringWriter writer = new StringWriter();
        template.merge(templateContext,writer);

        return writer.toString();
    }


    public Component toZulComponents(FormbuilderNodeRenderer renderer) {
        return renderNode(renderer, this.getRoot());
    }

    private Component renderNode(FormbuilderNodeRenderer renderer, FormbuilderNode node) {
        Div result = new Div();
        result.addSclass("formItem");
        if (node.getData() != null) {
            for (Component renderedChild : Arrays.asList(renderer.render(node.getData()))) {
                result.appendChild(renderedChild);
            }
        }
        Div childrenDiv = new Div();
        childrenDiv.setSclass("formItemChildren");
        result.appendChild(childrenDiv);
        for (TreeNode<FormbuilderItem> childNode : node.getChildren()) {
            childrenDiv.appendChild(renderNode(renderer, (FormbuilderNode) childNode));
        }
        return result;
    }


}
