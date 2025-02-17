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
		FormHelper.showUseInput();
	}

	/**
	 * assume you already have a form structure in mind.
	 */
	private void buildFormModel() {
		formModel = new FormModel();
		formModel.add(new FormField("Project Name", "shortText", "Summer Product Launch"));

		//create a field with 2nd level fields
		ArrayList<FormNode> node2children = new ArrayList<FormNode>();
		node2children.add(new FormNode(new FormField("Task Detail", "longText", "Design social media graphics"),new ArrayList<FormNode>()));
		node2children.add(new FormNode(new FormField("Assigned Team Member", "shortText", "Peter"),new ArrayList<FormNode>()));
		node2children.add(new FormNode(new FormField("Priority Level", "integer", "3"),new ArrayList<FormNode>()));
		formModel.add(new FormField("Main Task", "shortText", "Create Promotional Content"),node2children);

		formModel.add(new FormField("Deadline", "date", null));
	}

	@Listen("onClick=#buildZulFromAbstract")
	public void buildZulFromAbstract() {
		String zulData = formModel.toZul();

		Components.removeAllChildren(host);
		Executions.createComponentsDirectly(zulData, null, host, null);
	}
}
