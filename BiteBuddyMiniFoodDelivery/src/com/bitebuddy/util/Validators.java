package com.bitebuddy.util;

public final class Validators {
	private Validators() {
	}

	public static boolean validPhone(String phone) {
		return phone != null && phone.matches("\\d{10}");
	}

	public static void requireQty(int qty) {
		if (qty <= 0 || qty > 20)
			throw new IllegalArgumentException("Quantity must be 1..20");
	}
}