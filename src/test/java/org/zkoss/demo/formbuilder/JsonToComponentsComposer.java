package org.zkoss.demo.formbuilder;

import org.zkoss.formbuilder.*;
import org.zkoss.json.*;
import org.zkoss.json.parser.*;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.*;

import java.util.ArrayList;

/**
 * convert JSON data to ZK components directly without generating zul
 */
public class JsonToComponentsComposer extends SelectorComposer<Component> {

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
	public void build() {
		JSONArray jsonData = parseJsonString();
		buildFormModel(jsonData);
		recreate();
	}

	private void recreate() {
		Components.removeAllChildren(host);
		Vlayout formRoot = new Vlayout();
		Component zkComponents = formModel.toZulComponents(new DemoFormbuilderNodeRenderer());
		formRoot.appendChild(zkComponents);
		Button saveBtn = new Button("Save");
		saveBtn.setId("savebtn");
		formRoot.appendChild(saveBtn);
		host.appendChild(formRoot);
		try {
			new FormComposer().doAfterCompose(formRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildFormModel(JSONArray jsonData) {
		FormbuilderNode root = new FormbuilderNode(null, new ArrayList<FormbuilderNode>());
		for (Object jsonNode : jsonData) {
			if(jsonNode instanceof JSONObject) {
				JSONObject jsonObjectNode = (JSONObject) jsonNode;
				root.add(getNodeFromJsonObject(jsonObjectNode));
			}
		}
		formModel = new FormbuilderModel(root);
	}

	private JSONArray parseJsonString() {
		JSONParser parser = new JSONParser();
		JSONArray jsonData = null;
		try {
			jsonData = (JSONArray) parser.parse(source.getValue());
		}catch(ParseException e){
			e.printStackTrace();
		}
		return jsonData;
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
