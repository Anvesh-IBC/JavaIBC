package com.hms.util;

import java.util.concurrent.atomic.AtomicLong;

public final class Ids {
	private static final AtomicLong BK = new AtomicLong(1000);
	private static final AtomicLong PY = new AtomicLong(5000);
	private static final AtomicLong INV = new AtomicLong(9000);

	private Ids() {
	}

	public static String bookingId() {
		return "BK-" + BK.getAndIncrement();
	}

	public static String paymentId() {
		return "PY-" + PY.getAndIncrement();
	}

	public static String invoiceNo() {
		return "INV-" + INV.getAndIncrement();
	}
}
