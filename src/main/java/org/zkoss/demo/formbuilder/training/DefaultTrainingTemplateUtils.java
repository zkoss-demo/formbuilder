package org.zkoss.demo.formbuilder.training;

import java.util.HashMap;
import java.util.Map;

public class DefaultTrainingTemplateUtils {

	public static Map<? extends String, ? extends String> getDefaultFormbuilderItemTemplates() {
		Map<String, String> output = new HashMap<String, String>();
		String shortTextTemplate = "<label value=\"$nodeName$\" /><textbox value=\"$nodeValue$\" id=\"$nodeName$\" />";
		String longTextTemplate = "<label value=\"$nodeName$\" /><textbox value=\"$nodeValue$\" id=\"$nodeName$\" multiline=\"true\" height=\"250px\"/>";
		String intTemplate = "<label value=\"$nodeName$\" /><intbox value=\"$nodeValue$\" id=\"$nodeName$\" />";
		output.put("shortText", shortTextTemplate);
		output.put("longText", longTextTemplate);
		output.put("integer", intTemplate);
		return output;
	}
	
}
