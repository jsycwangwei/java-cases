package com.focustech.eventcenter.demo;

import com.focustech.eventcenter.EventTopic;

/**
 * 构建一个Topic，推荐做法为：定义一个枚举，此枚举实现了 {@link EventTopic}
 * 
 * @author wangyajun
 * @since 2016-1-20 下午3:19:14
 */
public class ConstructEventTopic {
	/**
	 * 定义eventTopic枚举
	 * 
	 * @author wangyajun
	 * @since 2016-1-20 下午3:21:32
	 */
	enum TestTopics implements EventTopic {
		testTopic1, testTopic2
	}
}
