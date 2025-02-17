package org.zkoss.formbuilder;

/**
 * Represents one field in the form e.g. first name, age.
 * Hold by {@link FormNode}.
 */
public class FormField {

	private String name; //input label
	private String type; //input type, determine to render with which field template
	private Object value; //the value of the input component, could be String or Number
	
	public FormField() {}

	public FormField(String name, String type, Object value) {
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
