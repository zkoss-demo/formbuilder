package org.zkoss.demo.formbuilder;

import org.zkoss.formbuilder.*;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.Div;

import java.util.ArrayList;

public class AbstractToZulComposer extends SelectorComposer<Component> {

	private FormbuilderModel formModel;
	
	@Wire
	private Div host;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		buildFormModel();
	}

	/**
	 * assume you already have a form structure in mind.
	 */
	private void buildFormModel() {
		FormbuilderNode root = new FormbuilderNode(new FormbuilderItem(),new ArrayList<FormbuilderNode>());
		formModel = new FormbuilderModel(root);
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

		Components.removeAllChildren(host);
		Executions.createComponentsDirectly(zulData, null, host, null);
	}
}
