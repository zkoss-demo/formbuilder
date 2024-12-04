package org.zkoss.demo.formbuilder;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listitem;

public interface FormbuilderNodeRenderer {

	public Component[] render(FormbuilderItem node);
	
}
