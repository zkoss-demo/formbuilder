package org.zkoss.formbuilder;

import org.zkoss.zk.ui.Component;

/**
 * create ZK components upon a {@link FormbuilderNode}
 */
public interface FormbuilderNodeRenderer {

	public Component[] render(FormbuilderItem node);
	
}
