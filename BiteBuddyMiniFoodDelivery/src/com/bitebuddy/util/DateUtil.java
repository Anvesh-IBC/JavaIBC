package com.bitebuddy.util;

import java.time.format.DateTimeFormatter;

public final class DateUtil {
	private DateUtil() {
	}

	public static final DateTimeFormatter RECEIPT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
