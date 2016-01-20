package com.focustech.eventcenter;

/**
 * 带有主题的事件中心
 * 
 * @author wangyajun
 * @since 2014-12-4 上午11:03:21
 */
public interface ITopicEventCenter {

	/**
	 * 添加对针对某一主题的EventListener
	 * 
	 * @param topic
	 *            主题名
	 * @param listener
	 *            监听器对象
	 */
	void addListener(EventTopic topic, IListener listener);

	/**
	 * 针对某一主题发布事件[同步]，任意{@link IListener#onEvent(Event)}抛出异常，将中断通知
	 * 
	 * @param topic
	 *            主题名
	 * @param eventSource
	 *            事件源，不能为null
	 * @param eventContent
	 *            事件内容
	 * 
	 * @see #publishEventSync(EventTopic, Object)
	 * @see #publishEventSync(EventTopic, boolean, Object, Object)
	 */
	void publishEventSync(EventTopic topic, Object eventSource, Object eventContent);

	/**
	 * 针对某一主题发布事件[同步]
	 * 
	 * @param topic
	 *            主题名
	 * @param interruptOnException
	 *            任意{@link IListener#onEvent(Event)}抛出异常，将中断通知
	 * @param eventSource
	 *            事件源，不能为null
	 * @param eventContent
	 *            事件内容
	 */
	void publishEventSync(EventTopic topic, boolean interruptOnException, Object eventSource, Object eventContent);

	/**
	 * 针对某一主题发布事件[同步]，忽略事件源，任意{@link IListener#onEvent(Event)}抛出异常，将中断通知
	 * 
	 * @param topic
	 *            主题名
	 * @param eventContent
	 *            事件内容
	 */
	void publishEventSync(EventTopic topic, Object eventContent);

	/**
	 * 针对某一主题发布事件[同步]，忽略事件源
	 * 
	 * @param topic
	 *            主题名
	 * @param interruptOnException
	 *            任意{@link IListener#onEvent(Event)}抛出异常，将中断通知
	 * @param eventContent
	 *            事件内容
	 */
	void publishEventSync(EventTopic topic, boolean interruptOnException, Object eventContent);

	/**
	 * 针对某一主题发布事件[异步]
	 * 
	 * @param topic
	 *            主题名
	 * @param eventSource
	 *            事件源，不能为null
	 * @param eventContent
	 *            事件内容
	 */
	void publishEventAsync(EventTopic topic, Object eventSource, Object eventContent);

	/**
	 * 针对某一主题发布事件[异步]，忽略事件源
	 * 
	 * @param topic
	 *            主题名
	 * @param eventContent
	 *            事件内容
	 */
	void publishEventAsync(EventTopic topic, Object eventContent);
}
