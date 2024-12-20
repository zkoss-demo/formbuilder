package org.zkoss.demo.formbuilder.training;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.demo.formbuilder.FormbuilderItem;
import org.zkoss.demo.formbuilder.FormbuilderNodeRenderer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class DemoFormbuilderNodeRenderer implements FormbuilderNodeRenderer {

	@Override
	public Component[] render(FormbuilderItem node) {
		List<Component> result = new ArrayList<Component>();
		switch (node.getType()) {
		case "integer":{
			Label label = new Label(node.getName());
			Intbox intbox = new Intbox((int) node.getValue());
			intbox.setId(node.getName());
			result.add(label);
			result.add(intbox);
			break;
		}
		case "shortText":{
			Label label = new Label(node.getName());
			Textbox textbox = new Textbox((String) node.getValue());
			textbox.setId(node.getName());
			result.add(label);
			result.add(textbox);
			break;
		}
		case "longText":{
			Label label = new Label(node.getName());
			Textbox textbox = new Textbox((String) node.getValue());
			textbox.setId(node.getName());
			textbox.setMultiline(true);
			textbox.setHeight("250px");
			result.add(label);
			result.add(textbox);
			break;
		}
		case "hiddenText":{
			Label label = new Label(node.getName());
			Textbox textbox = new Textbox((String) node.getValue());
			textbox.setId(node.getName());
			textbox.setType("password");
			result.add(label);
			result.add(textbox);
			break;
		}
		case "labelOnly":{
			Label label = new Label((String) node.getValue());
			result.add(label);
			break;
		}
		default:
			break;
		}
		return result.toArray(new Component[0]);
	}

}
