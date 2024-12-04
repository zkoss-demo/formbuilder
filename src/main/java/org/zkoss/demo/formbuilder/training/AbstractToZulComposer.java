package org.zkoss.demo.formbuilder.training;

import java.util.ArrayList;
import java.util.Collections;

import org.zkoss.demo.formbuilder.FormbuilderItem;
import org.zkoss.demo.formbuilder.FormbuilderModel;
import org.zkoss.demo.formbuilder.FormbuilderNode;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;

public class AbstractToZulComposer extends SelectorComposer<Component> {

	private FormbuilderModel formModel;
	
	@Wire
	private Div host;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		FormbuilderNode root = new FormbuilderNode(new FormbuilderItem(),new ArrayList<FormbuilderNode>());
		formModel = new FormbuilderModel(root);
		formModel.getFormbuilderItemTemplates().putAll(DefaultTrainingTemplateUtils.getDefaultFormbuilderItemTemplates());
		root.add(new FormbuilderNode(new FormbuilderItem("input1", "shortText", "foo"),new ArrayList<FormbuilderNode>()));
		ArrayList<FormbuilderNode> node2children = new ArrayList<FormbuilderNode>();
		node2children.add(new FormbuilderNode(new FormbuilderItem("input2-1", "integer", "10"),new ArrayList<FormbuilderNode>()));
		node2children.add(new FormbuilderNode(new FormbuilderItem("input2-2", "integer", "15"),new ArrayList<FormbuilderNode>()));
		node2children.add(new FormbuilderNode(new FormbuilderItem("input2-3", "longText", "FooBarBaz"),new ArrayList<FormbuilderNode>()));
		root.add(new FormbuilderNode(new FormbuilderItem("input2", "shortText", "bar"),node2children));
		root.add(new FormbuilderNode(new FormbuilderItem("input3", "shortText", "baz"),new ArrayList<FormbuilderNode>()));
	}
	
	@Listen("onClick=#buildZulFromAbstract")
	public void buildZulFromAbstract() {
		String zulData = formModel.toZulOutput();
		Clients.log(zulData);
		Components.removeAllChildren(host);
		Executions.createComponentsDirectly(zulData, null, host, null);
	}
	
}
