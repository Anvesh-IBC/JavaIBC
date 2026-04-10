package com.hms.config;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsConfig {

	public static ExecutorService ioPool() {
		return new ThreadPoolExecutor(4, // core threads
				12, // max threads
				60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), new ThreadFactoryBuilder("io-pool-%d"),
				new ThreadPoolExecutor.CallerRunsPolicy() // backpressure
		);
	}
}

// Simple ThreadFactory builder for naming threads
class ThreadFactoryBuilder implements ThreadFactory {

	private final String pattern;
	private final AtomicInteger idx = new AtomicInteger();

	ThreadFactoryBuilder(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setName(String.format(pattern, idx.incrementAndGet()));
		t.setDaemon(true);
		return t;
	}
}
