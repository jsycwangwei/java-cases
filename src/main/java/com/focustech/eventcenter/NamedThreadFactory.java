package com.focustech.eventcenter;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 命名的线程工厂
 * 
 * @author wangyajun
 * @since 2015-1-16 上午10:48:31
 */
public class NamedThreadFactory implements ThreadFactory {

	private final AtomicInteger threadIndexNum = new AtomicInteger(1);

	private final String threadPrefixName;

	private final ThreadGroup mGroup;

	private boolean daemon = false;

	public NamedThreadFactory(String prefix) {
		this(prefix, false);
	}

	public NamedThreadFactory(String prefix, boolean daemon) {
		threadPrefixName = prefix + "-thread-";
		this.daemon = daemon;
		SecurityManager s = System.getSecurityManager();
		mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable runnable) {
		String name = threadPrefixName + threadIndexNum.getAndIncrement();
		Thread thread = new Thread(mGroup, runnable, name, 0);
		thread.setDaemon(daemon);
		if (thread.getPriority() != Thread.NORM_PRIORITY)
			thread.setPriority(Thread.NORM_PRIORITY);
		return thread;
	}

}
