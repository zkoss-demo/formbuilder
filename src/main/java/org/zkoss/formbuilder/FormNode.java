package org.zkoss.formbuilder;

import java.util.*;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

/**
 * just a container to store {@link FormField}
 */
public class FormNode extends DefaultTreeNode<FormField> {

	public FormNode(FormField data) {
		super(data);
	}
	
	public FormNode(FormField data, Collection<? extends TreeNode<FormField>> children) {
		super(data, children);
	}

	public FormNode(FormField data, TreeNode<FormField>[] children) {
		super(data, children);
	}

	@Override
	public FormNode getChildAt(int childIndex) {
		return (FormNode) super.getChildAt(childIndex);
	}

	/** simplify adding a {@link FormField} without instantiating a {@link FormNode}*/
	public void add(FormField field){
		this.add(new FormNode(field, new LinkedList<>()));
	}

	public void add(FormField field, List children) {
		this.add(new FormNode(field, children));
	}

}
