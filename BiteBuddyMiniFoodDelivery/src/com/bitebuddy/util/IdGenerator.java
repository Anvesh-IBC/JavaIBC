package com.bitebuddy.util;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {
	private static final AtomicLong ORDER_SEQ = new AtomicLong(1_000_000L);

	private IdGenerator() {
	}

	public static long nextOrderId() {
		return ORDER_SEQ.incrementAndGet();
	}
}
