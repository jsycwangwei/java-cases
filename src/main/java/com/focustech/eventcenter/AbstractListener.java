package com.focustech.eventcenter;

/**
 * 抽象的监听者
 * 
 * @author wangyajun
 * @since 2014-12-16 上午11:20:00
 */
public abstract class AbstractListener implements IListener {

	@Override
	public String getId() {
		return this.toString();
	}

	/**
	 * 校验content（非空以及类型匹配校验）
	 * 
	 * @param event
	 * @param expectedContentType
	 *            期望的内容类型
	 * @return
	 */
	protected boolean validatedContent(Event event, Class<?> expectedContentType) {
		return event != null && event.getContent() != null && expectedContentType.isInstance(event.getContent());
	}
}
