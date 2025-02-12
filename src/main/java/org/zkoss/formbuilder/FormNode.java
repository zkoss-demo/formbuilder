package org.zkoss.formbuilder;

import java.util.Collection;

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
	

}
