package com.focustech.eventcenter.demo;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.focustech.eventcenter.AbstractTopicEventCenter;
import com.focustech.eventcenter.DefaultTopicEventCenter;
import com.focustech.eventcenter.IEventCenterHolder;

/**
 * 测试构建事件中心实例，当然你也可以实现 {@link AbstractTopicEventCenter}
 * 
 * @author wangyajun
 * @since 2016-1-20 下午12:11:19
 */
public class ConstructEventCenter implements IEventCenterHolder {

	static void constructDiyEventCenter() {
		// 1 线程池大小为cpu*2
		DefaultTopicEventCenter eventCenter = new DefaultTopicEventCenter();

		// 2 自定义线程池
		ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
		eventCenter = new DefaultTopicEventCenter(executor);

		// 3 重新设置线程池大小
		ThreadPoolExecutor newExecutor = new ThreadPoolExecutor(6, 6, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
		eventCenter.setThreadPool(newExecutor);
	}

	/**
	 * 使用默认的线程池实例，只需要实现 {@link IEventCenterHolder}，将会提供含有cpu*2个线程的
	 * {@link DefaultTopicEventCenter}
	 */
	static void useDefault() {
		System.out.println(eventCenter);
	}
}
