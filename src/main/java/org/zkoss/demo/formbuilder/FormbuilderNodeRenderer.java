package org.zkoss.demo.formbuilder;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listitem;

/**
 * create ZK components upon a {@link FormbuilderNode}
 */
public interface FormbuilderNodeRenderer {

	public Component[] render(FormbuilderItem node);
	
}
