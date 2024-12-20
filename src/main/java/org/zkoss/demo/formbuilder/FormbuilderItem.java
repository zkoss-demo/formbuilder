package org.zkoss.demo.formbuilder;

import java.util.Map;

/**
 * Represents the abstract data of the form element.
 * Used with FormbuilderNode
 */
public class FormbuilderItem {

	private String name;
	private String type;
	private Object value;
	
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
