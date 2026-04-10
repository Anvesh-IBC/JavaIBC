package com.ibc.training.shop;

import java.util.concurrent.atomic.AtomicInteger;

final class IdGen {
	private static final AtomicInteger SEQ = new AtomicInteger(1000);

	private IdGen() {
	}

	static int nextId() {
		return SEQ.incrementAndGet();
	}
}