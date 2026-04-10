package com.hms.util;

import com.hms.model.RoomType;

public final class PriceCalculator {
	private PriceCalculator() {
	}

	public static double computeNightly(RoomType type, double base) {
		double uplift = 0;
		switch (type) {
		case DELUXE:
			uplift = 1000;
			break;
		case SUITE:
			uplift = 2000;
			break;
		case STANDARD:
		default:
			uplift = 0;
		}
		return base + uplift;
	}

	public static double computeTax(double subtotal) {
		return subtotal * 0.12; // 12% GST example
	}

	public static double computeDiscount(int stayDays) {
		return stayDays >= 7 ? 0.10 : 0.0; // 10% for long stays
	}

	public static double computeTotal(double nightly, int days) {
		double subtotal = nightly * days;
		double tax = computeTax(subtotal);
		double discount = computeDiscount(days) * subtotal;
		return subtotal + tax - discount;
	}
}
