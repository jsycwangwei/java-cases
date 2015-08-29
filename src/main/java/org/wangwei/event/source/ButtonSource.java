package org.wangwei.event.source;

import java.util.LinkedList;
import java.util.List;
import org.wangwei.event.events.Event;
import org.wangwei.event.listener.EventListener;

public class ButtonSource implements EventSource {

    protected List<EventListener<? extends Event>> listeners = new LinkedList<EventListener<? extends Event>>();


    public void addListener(EventListener<? extends Event> listener) {
        listeners.add(listener);
    }

    public void removeListener(EventListener<? extends Event> listener) {
        listeners.remove(listener);
    }

    public void notifyAll(Event event) {
        for (EventListener listener : listeners) {
            try {
                listener.handleEvent(event);
            } catch (ClassCastException e) {
            }
        }
    }


}