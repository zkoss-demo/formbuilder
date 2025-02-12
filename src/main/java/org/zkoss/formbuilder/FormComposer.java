package org.zkoss.formbuilder;

import java.util.*;

import com.google.gson.JsonObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.*;
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
	public static String RESULT_KEY = "formResult";

	@Wire("textbox, intbox")
	protected List<InputElement> inputElements;
	public static String EVENT_QUEUE_NAME = "formEvent";
	public static String EVENT_SAVE = "onSaveForm";
	protected EventQueue eventQueue = EventQueues.lookup(EVENT_QUEUE_NAME, true); //desktop-level

	@Listen("onClick=#savebtn")
	public void doSaveForm() {
		JsonObject result = new JsonObject();
		for (Iterator<InputElement> iterator = inputElements.iterator(); iterator.hasNext();) {
			InputElement comp = iterator.next();
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
			result.addProperty(comp.getId(), value.toString());
		}
		getPage().getDesktop().setAttribute(RESULT_KEY, result.toString());
		eventQueue.publish(createSaveEvent(result.toString()));
	}

	protected Event createSaveEvent(String result){
		return new Event(EVENT_SAVE, null, result);
	}
}
