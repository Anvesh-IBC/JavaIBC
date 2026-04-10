package com.hms.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Dates {
	private static final DateTimeFormatter ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Dates() {
	}

	public static LocalDate parse(String s) {
		return LocalDate.parse(s.trim(), ISO);
	}

	public static String format(LocalDate d) {
		return d.format(ISO);
	}
}
