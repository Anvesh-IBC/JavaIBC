package com.hms.util;

import com.hms.model.RoomType;
import com.hms.service.RatesCatalog;

import java.time.LocalDate;

public final class PriceCalculator {

	private static RatesCatalog ratesCatalog;

	private PriceCalculator() {
	}

	// Must be called once during application startup
	public static void init(RatesCatalog catalog) {
		ratesCatalog = catalog;
	}

	// Nightly price now comes from RatesCatalog
	public static double computeNightly(RoomType type, LocalDate date) {
		if (ratesCatalog == null) {
			throw new IllegalStateException("RatesCatalog not initialized");
		}
		return ratesCatalog.price(type.name(), date);
	}

	public static double computeTax(double subtotal) {
		return subtotal * 0.12; // 12% GST example
	}

	public static double computeDiscount(int stayDays) {
		return stayDays >= 7 ? 0.10 : 0.0;
	}

	public static double computeTotal(double nightly, int days) {
		double subtotal = nightly * days;
		double tax = computeTax(subtotal);
		double discount = computeDiscount(days) * subtotal;
		return subtotal + tax - discount;
	}
}
