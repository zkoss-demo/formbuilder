package org.zkoss.demo.formbuilder;

import org.zkoss.formbuilder.*;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.Div;

import java.util.ArrayList;

public class AbstractToZulComposer extends SelectorComposer<Component> {

	private FormModel formModel;
	
	@Wire
	private Div host;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		buildFormModel();
		EventListener eventListener = event -> {
			Notification.show(event.getData().toString());
		};
		FormHelper.subscribeFormSave(eventListener);
	}

	/**
	 * assume you already have a form structure in mind.
	 */
	private void buildFormModel() {
		FormNode root = new FormNode(new FormField(),new ArrayList<FormNode>());
		formModel = new FormModel(root);
		root.add(new FormNode(new FormField("input1", "shortText", "foo"),new ArrayList<FormNode>()));
		ArrayList<FormNode> node2children = new ArrayList<FormNode>();
		node2children.add(new FormNode(new FormField("input2-1", "integer", "10"),new ArrayList<FormNode>()));
		node2children.add(new FormNode(new FormField("input2-2", "integer", "15"),new ArrayList<FormNode>()));
		node2children.add(new FormNode(new FormField("input2-3", "longText", "FooBarBaz"),new ArrayList<FormNode>()));
		root.add(new FormNode(new FormField("input2", "shortText", "bar"),node2children));
		root.add(new FormNode(new FormField("input3", "shortText", "baz"),new ArrayList<FormNode>()));
	}

	@Listen("onClick=#buildZulFromAbstract")
	public void buildZulFromAbstract() {
		String zulData = formModel.toZul();

		Components.removeAllChildren(host);
		Executions.createComponentsDirectly(zulData, null, host, null);
	}
}
