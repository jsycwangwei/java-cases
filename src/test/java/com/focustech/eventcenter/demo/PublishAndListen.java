package com.focustech.eventcenter.demo;

import com.focustech.eventcenter.AbstractListener;
import com.focustech.eventcenter.Event;
import com.focustech.eventcenter.IEventCenterHolder;
import com.focustech.eventcenter.demo.ConstructEventTopic.TestTopics;

/**
 * 发布和监听，完整使用，使用默认的事件中心实例
 * 
 * @author wangyajun
 * @since 2016-1-20 下午3:23:11
 */
public class PublishAndListen implements IEventCenterHolder {
	/**
	 * 添加监听器
	 */
	static void addListeners() {
		// add Listener1
		eventCenter.addListener(TestTopics.testTopic1, new AbstractListener() {

			@Override
			public void onEvent(Event event) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}

				System.out.println("【Listener1】：deal " + event);
				throw new RuntimeException("only for test");
			}
		});

		// add Listener2
		eventCenter.addListener(TestTopics.testTopic1, new AbstractListener() {

			@Override
			public void onEvent(Event event) {
				System.out.println("【Listener2】：deal " + event);
			}
		});
	}

	static void publishEvent() {
		System.out.println(">>>>>>>>>>>>>> 异步发布事件 ");
		eventCenter.publishEventAsync(TestTopics.testTopic1, "TestPublishAndListen");

		System.out.println(eventCenter);

		System.out.println(">>>>>>>>>>>>>> 同步发布事件，并且当一个Listener抛出异常时，不影响其他Listener");
		eventCenter.publishEventSync(TestTopics.testTopic1, false, "TestPublishAndListen_sync");

		System.out.println(">>>>>>>>>>>>>> 同步发布事件，并且当一个Listener抛出异常时，将中断其他Listener");
		eventCenter.publishEventSync(TestTopics.testTopic1, true, "TestPublishAndListen_sync");
	}

	public static void main(String[] args) {
		addListeners();
		publishEvent();
	}
}
