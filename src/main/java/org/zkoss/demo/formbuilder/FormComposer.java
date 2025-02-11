package org.zkoss.demo.formbuilder;

import java.util.*;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.util.*;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

/**
 * default composer for the generated form
 */
public class FormComposer extends SelectorComposer {

	@Wire("textbox, intbox")
	List<InputElement> inputElements;

	@Listen("onClick=#savebtn")
	public void doSaveForm() {
		Map<String, Object> returnedValues = new HashMap<String, Object>();
		for (Iterator<InputElement> iterator = inputElements.iterator(); iterator.hasNext();) {
			Component comp = iterator.next();
			Object value = null;
			switch (comp.getClass().getSimpleName()) {
			case "Textbox":
				value = ((Textbox) comp).getValue();
				break;
			case "Intbox":
				value = ((Intbox) comp).getValue();
				break;
			default:
				break;
			}
			returnedValues.put(comp.getId(), value);
		}
		Notification.show(returnedValues.toString());
	}
}
