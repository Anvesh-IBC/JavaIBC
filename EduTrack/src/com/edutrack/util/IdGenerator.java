package com.edutrack.util;

public final class IdGenerator {
	private static int studentSeq;

	static {
		// Static Initialization Block
		studentSeq = 1000;
	}

	private IdGenerator() {
	}

	public static synchronized int nextStudentId() {
		return ++studentSeq;
	}
}