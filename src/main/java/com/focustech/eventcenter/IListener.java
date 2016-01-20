package com.focustech.eventcenter;

import java.util.EventListener;

/**
 * 事件监听器，监听者在收到事件后推荐通过线程隔离异步处理
 * 
 * @author wangyajun
 * @since 2014-12-4 上午10:28:49
 */
public interface IListener extends EventListener {

	/**
	 * 返回Listener的唯一标识，避免Listener重复
	 * 
	 * @return String 监听器的唯一标识
	 */
	String getId();

	/**
	 * 事件响应动作
	 * 
	 * @param event
	 *            事件
	 */
	void onEvent(Event event);
}
