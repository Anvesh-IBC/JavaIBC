package com.edutrack.util;

import java.util.regex.Pattern;

public final class Validators {
	private Validators() {
	}

	private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
	private static final Pattern PHONE = Pattern.compile("^\\d{10}$");

	public static boolean isValidEmail(String email) {
		return email != null && EMAIL.matcher(email.trim()).matches();
	}

	public static boolean isValidPhone(String phone) {
		return phone != null && PHONE.matcher(phone.trim()).matches();
	}

	public static boolean isValidAge(byte age) {
		return age >= 16 && age <= 100;
	}

	public static boolean isValidName(String name) {
		return name != null && name.trim().length() >= 2;
	}
}