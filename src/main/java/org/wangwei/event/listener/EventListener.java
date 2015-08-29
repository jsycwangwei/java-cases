package org.wangwei.event.listener;

import org.wangwei.event.events.Event;

/**
 * @author jsycwangwei
 * @since 15/8/29 下午12:27
 */
public interface EventListener<T extends Event>{

    public void handleEvent(Event event);
}
