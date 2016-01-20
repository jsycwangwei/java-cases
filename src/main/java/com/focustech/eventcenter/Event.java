package com.focustech.eventcenter;

import java.util.EventObject;

/**
 * 事件对象，异常对象只能放在 {@link EventObject#getSource()}
 * 
 * <pre>
 * 主要属性：
 * 1 content:存放任意对象
 * 2 source：事件源只能是触发此事件的对象
 * 
 * @author wangyajun
 * @since 2014-12-4 上午10:26:21
 */
public final class Event extends EventObject {
	private static final long serialVersionUID = -4801623587669565581L;

	private final Object content;

	private EventTopic topic;

	private long eventId;

	private boolean isAsyncPublish = true;

	private boolean interruptOnException = true;

	/**
	 * @param source
	 *            事件源,不能为null【注：序列化场景中，此参数会被忽略】
	 * @param _content
	 *            事件的消息内容
	 */
	Event(Object source, Object _content) {
		super(source);
		this.content = _content;
	}

	public EventTopic getTopic() {
		return topic;
	}

	void setTopic(EventTopic topic) {
		this.topic = topic;
	}

	public Object getContent() {
		return content;
	}

	public long getEventId() {
		return eventId;
	}

	void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public boolean isAsyncPublish() {
		return isAsyncPublish;
	}

	void setAsyncPublish(boolean isAsyncPublish) {
		this.isAsyncPublish = isAsyncPublish;
	}

	public boolean isInterruptOnException() {
		return interruptOnException;
	}

	void setInterruptOnException(boolean interruptOnException) {
		this.interruptOnException = interruptOnException;
	}

	@Override
	public String toString() {
		return "Event [content=" + content + ", topic=" + topic + ", eventId=" + eventId + ", isAsyncPublish=" + isAsyncPublish
				+ ", interruptOnException=" + interruptOnException + "]";
	}

}
