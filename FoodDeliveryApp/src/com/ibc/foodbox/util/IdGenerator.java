package com.ibc.foodbox.util;

public final class IdGenerator {
	private static int next = 1;

	private IdGenerator() {
	}

	public static synchronized int nextId() {
		return next++;
	}
}