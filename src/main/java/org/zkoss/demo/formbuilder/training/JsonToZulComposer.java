package org.zkoss.demo.formbuilder.training;

import java.util.ArrayList;

import org.zkoss.demo.formbuilder.*;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.json.parser.ParseException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;


public class JsonToZulComposer extends SelectorComposer<Component> {

	private FormbuilderModel formModel;
	
	@Wire
	private Component host;
	
	@Wire
	Textbox source;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	@Listen("onClick=#buildZulFromJson")
	public void buildZulFromAbstract() {
		JSONParser parser = new JSONParser();
		JSONArray jsonData = null;
		try {
			jsonData = (JSONArray) parser.parse(source.getValue());
		}catch(ParseException e){
			e.printStackTrace();
		}

		FormbuilderNode root = new FormbuilderNode(null, new ArrayList<FormbuilderNode>());
		for (Object jsonNode : jsonData) {
			if(jsonNode instanceof JSONObject) {
				JSONObject jsonObjectNode = (JSONObject) jsonNode;
				root.add(getNodeFromJsonObject(jsonObjectNode));
			}
		}
		formModel = new FormbuilderModel(root);
		formModel.getFormbuilderItemTemplates().put("hiddenText", "<label value=\"$nodeName$\" /><textbox type=\"password\" value=\"$nodeValue$\" id=\"$nodeName$\" />");
		formModel.getFormbuilderItemTemplates().put("labelOnly", "<label value=\"$nodeValue$\" />");
		String zulData = formModel.toZulOutput();

		Components.removeAllChildren(host);
		Executions.createComponentsDirectly(zulData, null, host, null);
	}

	private FormbuilderNode getNodeFromJsonObject(JSONObject jsonObjectNode) {
		
		String name = (String) jsonObjectNode.get("name");
		String type = (String) jsonObjectNode.get("type");
		Object value = jsonObjectNode.get("value");
		
		ArrayList<FormbuilderNode> children = new ArrayList<FormbuilderNode>();
		JSONArray childArray = (JSONArray) jsonObjectNode.get("children");
		if(childArray != null) {
			for (Object child : childArray) {
				children.add(getNodeFromJsonObject((JSONObject) child));
			}
		}
		FormbuilderNode newNode = new FormbuilderNode(new FormbuilderItem(name, type, value), children);
		return newNode;
	}
	
}
