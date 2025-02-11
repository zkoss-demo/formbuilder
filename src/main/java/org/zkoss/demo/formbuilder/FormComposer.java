package org.zkoss.demo.formbuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

/**
 * default composer for the generated form
 */
public class FormComposer extends SelectorComposer {

	@Listen("onClick=#savebtn")
	public void doSaveForm() {
		Collection<Component> fellows = getSelf().getFellows();
		Map<String, Object> returnedValues = new HashMap<String, Object>();
		for (Iterator<Component> iterator = fellows.iterator(); iterator.hasNext();) {
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

	}
}
