package com.hms.util;

import java.util.regex.Pattern;

public final class Validators {
	private static final Pattern PHONE = Pattern.compile("\\d{10}");
	private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

	private Validators() {
	}

	public static boolean isValidPhone(String phone) {
		return phone != null && PHONE.matcher(phone.trim()).matches();
	}

	public static boolean isValidEmail(String email) {
		return email != null && EMAIL.matcher(email.trim()).matches();
	}
}
