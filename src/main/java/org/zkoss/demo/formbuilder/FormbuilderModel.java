package org.zkoss.demo.formbuilder;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Div;
import org.zkoss.zul.TreeNode;

import com.google.common.base.Strings;

public class FormbuilderModel extends AbstractTreeModel<FormbuilderNode> {

	private Map<String,String> formbuilderItemTemplates;
	private Template template;

	public FormbuilderModel(FormbuilderNode root) {
		super(root);
		formbuilderItemTemplates = new HashMap<String, String>();
		initTemplateEngine();
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

	public String toZulOutput() {
		List<String> rowsContent = new LinkedList();
		for (TreeNode<FormbuilderItem> node : this.getRoot().getChildren()) {
			nodeToRows(node, rowsContent);
		}
		VelocityContext templateContext = new VelocityContext();
		templateContext.put("rowsContent", rowsContent);
		return produceZulContent(templateContext);
	}

	private String produceZulContent(VelocityContext templateContext) {
		StringWriter writer =  new StringWriter();
		template.merge(templateContext, writer);
		return writer.toString();
	}

	private void initTemplateEngine() {
		//setup to load template from classpath
		Properties properties = new Properties();
		properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		VelocityEngine velocityEngine = new VelocityEngine(properties);
		velocityEngine.init();
		template = velocityEngine.getTemplate("formTemplate.zul");
	}

	private String nodeToZul(TreeNode<FormbuilderItem> node) {
		StringBuffer output = new StringBuffer();
		output.append("<div sclass=\"formItem\">\n");
		output.append(renderNodeTemplate(node));
		output.append("<div sclass=\"formItemChildren\">\n");
		for (TreeNode<FormbuilderItem> childNode : node.getChildren()) {
			output.append(nodeToZul(childNode));
		}
		output.append("</div>\n");
		output.append("</div>\n");
		return output.toString();
	}

	
	private void nodeToRows(TreeNode<FormbuilderItem> node, List<String> rowsContent) {
		rowsContent.add(renderNodeTemplate(node));
		for (TreeNode<FormbuilderItem> childNode : node.getChildren()) {
			nodeToRows(childNode, rowsContent);
		}
	}

	private String renderNodeTemplate(TreeNode<FormbuilderItem> node) {
		String templateString = formbuilderItemTemplates.get(node.getData().getType());
		if(
			Strings.isNullOrEmpty(templateString)
		)
		throw new RuntimeException("template not found: " + node.getData().getType());
		if(
			Strings.isNullOrEmpty(node.getData().getName())
		)
		throw new RuntimeException("Required name (name:" + node.getData().getName()+")");
			
		templateString = templateString.replace("$nodeValue$", String.valueOf(node.getData().getValue()));
		templateString = templateString.replace("$nodeName$", node.getData().getName());
		return templateString;
	}


	public Component toZulComponents(FormbuilderNodeRenderer renderer) {
		return renderNode(renderer, this.getRoot());
	}
	
	private Component renderNode(FormbuilderNodeRenderer renderer, FormbuilderNode node) {
		Div result = new Div();
		result.addSclass("formItem");
		if(node.getData() != null) {
			for (Component renderedChild : Arrays.asList(renderer.render(node.getData()))) {
				result.appendChild(renderedChild);
			}
		}
		Div childrenDiv = new Div();
		childrenDiv.setSclass("formItemChildren");
		result.appendChild(childrenDiv);
		for (TreeNode<FormbuilderItem> childNode : node.getChildren()) {
			childrenDiv.appendChild(renderNode(renderer,(FormbuilderNode) childNode));
		}
		return result;
	}

	public Map<String, String> getFormbuilderItemTemplates() {
		return formbuilderItemTemplates;
	}

	public void setFormbuilderItemTemplates(Map<String, String> formbuilderItemTemplates) {
		this.formbuilderItemTemplates = formbuilderItemTemplates;
	}
	
	
}
