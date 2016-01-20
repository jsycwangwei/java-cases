package com.focustech.eventcenter;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象主题事件中心
 * 
 * @author wangyajun
 * @since 2014-12-4 上午11:07:00
 */
public abstract class AbstractTopicEventCenter implements ITopicEventCenter {
	protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultTopicEventCenter.class);

	@Override
	public void publishEventSync(EventTopic topic, Object eventSource, Object eventContent) {
		publishEventSync(topic, true, eventSource, eventContent);
	}

	@Override
	public void publishEventSync(EventTopic topic, boolean interruptOnException, Object eventSource, Object eventContent) {
		Event event = new Event(eventSource, eventContent);
		event.setAsyncPublish(false);
		event.setInterruptOnException(interruptOnException);
		publishEvent(topic, event);
	}

	@Override
	public void publishEventSync(EventTopic topic, boolean interruptOnException, Object eventContent) {
		publishEventSync(topic, interruptOnException, this, eventContent);
	}

	@Override
	public void publishEventSync(EventTopic topic, Object eventContent) {
		publishEventSync(topic, this, eventContent);
	}

	void publishEventAsync(EventTopic topic, Event event) {
		event.setAsyncPublish(true);
		publishEvent(topic, event);
	}

	@Override
	public void publishEventAsync(EventTopic topic, Object eventSource, Object eventContent) {
		publishEventAsync(topic, new Event(eventSource, eventContent));
	}

	@Override
	public void publishEventAsync(EventTopic topic, Object eventContent) {
		publishEventAsync(topic, this, eventContent);
	}

	/**
	 * 在所有监听器中是否存在ID与_listener一致的监听器
	 * 
	 * @param _listener
	 *            被查找的监听器
	 * @param listeners
	 *            监听器集合
	 * @return true存在 false不存在
	 */
	protected final boolean exists(IListener _listener, BlockingQueue<IListener> listeners) {
		for (IListener listener : listeners) {
			if (listener.getId().equals(_listener.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 针对某一主题发布事件
	 * 
	 * @param topic
	 * @param event
	 */
	protected abstract void publishEvent(final EventTopic topic, final Event event);

}
