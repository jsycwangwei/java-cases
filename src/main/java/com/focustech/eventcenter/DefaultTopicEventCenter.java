package com.focustech.eventcenter;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 简单的带有Topic的事件中心
 * 
 * @author wangyajun
 * @since 2014-12-4 下午3:46:15
 */
public final class DefaultTopicEventCenter extends AbstractTopicEventCenter {

	private static final AtomicLong eventIdCounter = new AtomicLong(1);

	protected ThreadPoolExecutor threadPool;

	/**
	 * 主题与其监听器集合映射,由子类实例化
	 */
	protected final Map<EventTopic, BlockingQueue<IListener>> topicListenersMap = new ConcurrentHashMap<EventTopic, BlockingQueue<IListener>>();

	public DefaultTopicEventCenter() {
		int cpuNum = Runtime.getRuntime().availableProcessors();
		this.threadPool = new ThreadPoolExecutor(cpuNum * 2, cpuNum * 2, 60, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("EventCenter", true));
		this.threadPool.allowCoreThreadTimeOut(true);
	}

	public DefaultTopicEventCenter(ThreadPoolExecutor threadPool) {
		setThreadPool(threadPool);
	}

	@Override
	public void addListener(EventTopic topic, IListener listener) {
		LOGGER.info("add Listenr topic:" + topic + "|" + listener);
		BlockingQueue<IListener> listeners = topicListenersMap.get(topic);
		if (listeners == null) {
			synchronized (topic) {
				listeners = topicListenersMap.get(topic);
				if (listeners == null) {
					listeners = new LinkedBlockingQueue<IListener>();
					topicListenersMap.put(topic, listeners);
				}
			}

		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	void logEvent(Event event) {
		if (event == null)
			return;
		Throwable exception = null;
		StringBuilder msg = new StringBuilder();
		msg.append("【").append(event.getTopic()).append("】");
		msg.append("eid:").append(event.getEventId()).append(",content:").append(event.getContent());
		if (event.getSource() instanceof Throwable) {
			exception = (Throwable) event.getSource();
		}
		if (exception != null) {
			LOGGER.error(msg.toString(), exception);
		} else {
			LOGGER.info(msg.toString(), exception);
		}
	}

	void dealEvent(IListener listener, Event event, boolean interruptOnException) {
		if (interruptOnException) {
			listener.onEvent(event);
		} else {
			try {
				listener.onEvent(event);
			} catch (Exception e) {
				LOGGER.error(listener + ": fail deal " + event);
			}
		}
	}

	@Override
	protected void publishEvent(final EventTopic topic, final Event event) {
		event.setEventId(eventIdCounter.getAndIncrement());
		event.setTopic(topic);
		logEvent(event);
		if (topicListenersMap.get(topic) == null || topicListenersMap.get(topic).isEmpty()) {
			return;
		}
		for (final IListener listener : topicListenersMap.get(topic)) {
			if (event.isAsyncPublish()) {
				getThreadPool().execute(new Runnable() {

					@Override
					public void run() {
						dealEvent(listener, event, false);
					}
				});
			} else {
				if (event.isInterruptOnException()) {
					dealEvent(listener, event, true);
				} else {
					dealEvent(listener, event, false);
				}
			}
		}
	}

	public ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolExecutor threadPool) {
		if (threadPool == null)
			throw new NullPointerException("threadpool");
		if (this.threadPool != null && !this.threadPool.isShutdown()) {
			this.threadPool.shutdown();
		}
		this.threadPool = threadPool;
	}

	@Override
	public String toString() {
		String sep = "\n";
		StringBuilder desc = new StringBuilder("#--------------TopicEventCenter Desc--------------#").append(sep);
		desc.append("#-----ThreadPoolStatus-----#").append(sep);
		desc.append("# corePoolSize = ").append(threadPool.getCorePoolSize()).append(sep);
		desc.append("# maximumPoolSize = ").append(threadPool.getMaximumPoolSize()).append(sep);
		desc.append("# activeThreadSize = ").append(threadPool.getActiveCount()).append(sep);
		desc.append("# taskQueueSize = ").append(threadPool.getQueue().size()).append(sep);
		desc.append("# completeTaskCount = ").append(threadPool.getCompletedTaskCount()).append(sep);
		desc.append("#-----TopicAndListener-----#").append(sep);
		Iterator<Map.Entry<EventTopic, BlockingQueue<IListener>>> iterator = topicListenersMap.entrySet().iterator();
		Map.Entry<EventTopic, BlockingQueue<IListener>> entry = null;
		while (iterator.hasNext()) {
			entry = iterator.next();
			desc.append("# " + entry.getKey() + " : ").append(entry.getValue()).append(sep);
		}
		return desc.toString();
	}
}
