package com.ibc.training.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidationUtil {
	private ValidationUtil() {
	}

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
	private static final Pattern COURSE_CODE_PATTERN = Pattern.compile("^CRS-\\d{3}$");

	public static boolean isValidEmail(String email) {
		if (email == null)
			return false;
		Matcher m = EMAIL_PATTERN.matcher(email);
		return m.matches();
	}

	public static boolean isValidCourseCode(String code) {
		if (code == null)
			return false;
		return COURSE_CODE_PATTERN.matcher(code).matches();
	}
}
