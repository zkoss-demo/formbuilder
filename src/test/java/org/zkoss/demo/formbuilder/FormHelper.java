package org.zkoss.demo.formbuilder;

import org.zkoss.formbuilder.FormComposer;
import org.zkoss.zk.ui.event.*;

public class FormHelper {
    static void subscribeFormSave(EventListener eventListener) {
        EventQueue queue = EventQueues.lookup(FormComposer.EVENT_QUEUE_NAME);
        queue.subscribe(eventListener);
    }
}
