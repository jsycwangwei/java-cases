package com.focustech.eventcenter;

/**
 * 默认事件中心持有者接口
 * 
 * @author wangyajun
 * @since 2015-1-26 下午4:12:13
 */
public interface IEventCenterHolder {
	ITopicEventCenter eventCenter = new DefaultTopicEventCenter();
}
