package org.zkoss.formbuilder;

import java.util.Collection;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

/**
 * just a container to store {@link FormbuilderItem}
 */
public class FormbuilderNode extends DefaultTreeNode<FormbuilderItem> {

	public FormbuilderNode(FormbuilderItem data) {
		super(data);
	}
	
	public FormbuilderNode(FormbuilderItem data, Collection<? extends TreeNode<FormbuilderItem>> children) {
		super(data, children);
	}

	public FormbuilderNode(FormbuilderItem data, TreeNode<FormbuilderItem>[] children) {
		super(data, children);
	}

	@Override
	public FormbuilderNode getChildAt(int childIndex) {
		return (FormbuilderNode) super.getChildAt(childIndex);
	}
	

}
