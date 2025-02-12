package org.zkoss.formbuilder;

/**
 * Represents one field in the form e.g. first name, age.
 * Hold by {@link FormbuilderNode}.
 */
public class FormbuilderItem {

	private String name; //input label
	private String type; //input type, maps to an input template
	private Object value; //the value of the input component, could be String or Number
	
	public FormbuilderItem() {
		super();
	}
	public FormbuilderItem(String name, String type, Object value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
