package org.wangwei.event.source;

import org.wangwei.event.events.Event;
import org.wangwei.event.listener.EventListener;

/**
 * @author jsycwangwei
 * @since 15/8/29 下午12:30
 */
public interface EventSource {
    void addListener(EventListener<? extends Event> listener);

    void removeListener(EventListener<? extends Event> listener);

    void notifyAll(Event event);


}
