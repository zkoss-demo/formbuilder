package org.zkoss.demo.formbuilder;

import org.zkoss.formbuilder.*;
import org.zkoss.json.*;
import org.zkoss.json.parser.*;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.Textbox;

import java.util.ArrayList;

/**
 * convert Json data to zul content
 */
public class JsonToZulComposer extends SelectorComposer<Component> {

	private FormModel formModel;
	
	@Wire
	private Component host;
	
	@Wire
	Textbox source;

	@Listen("onClick=#buildZulFromJson")
	public void build() {
		JSONArray jsonData = parseJsonString();
		buildFormModel(jsonData);
		recreate();
		FormHelper.showUseInput();
	}

	private void recreate() {
        Components.removeAllChildren(host);
		Executions.createComponentsDirectly(formModel.toZul(), null, host, null);
	}

	private void buildFormModel(JSONArray jsonData) {
		formModel = new FormModel();
		FormNode root = formModel.getRoot();
		for (Object jsonNode : jsonData) {
			if(jsonNode instanceof JSONObject) {
				JSONObject jsonObjectNode = (JSONObject) jsonNode;
				root.add(getNodeFromJsonObject(jsonObjectNode));
			}
		}
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

	private FormNode getNodeFromJsonObject(JSONObject jsonObjectNode) {
		
		String name = (String) jsonObjectNode.get("name");
		String type = (String) jsonObjectNode.get("type");
		Object value = jsonObjectNode.get("value");
		
		ArrayList<FormNode> children = new ArrayList<FormNode>();
		JSONArray childArray = (JSONArray) jsonObjectNode.get("children");
		if(childArray != null) {
			for (Object child : childArray) {
				children.add(getNodeFromJsonObject((JSONObject) child));
			}
		}
		FormNode newNode = new FormNode(new FormField(name, type, value), children);
		return newNode;
	}
	
}
