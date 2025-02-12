package org.zkoss.formbuilder;

import org.zkoss.zk.ui.Component;

/**
 * create ZK components upon a {@link FormNode}
 */
public interface FormbuilderNodeRenderer {

	public Component[] render(FormField node);
	
}
