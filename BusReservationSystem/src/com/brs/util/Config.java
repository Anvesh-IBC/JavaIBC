package com.brs.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Static configuration: - Demonstrates static initializer block for
 * banner/locale/GST - Holds INR currency formatter and GST constant
 */
public final class Config {
	public static final double DEFAULT_GST;
	public static final Locale INDIA_LOCALE;
	public static final NumberFormat INR;

	static {
		INDIA_LOCALE = new Locale("en", "IN");
		INR = NumberFormat.getCurrencyInstance(INDIA_LOCALE);
		DEFAULT_GST = 0.05; // 5% GST for Phase 1

		// Banner printed when class first loads
		System.out.println("==============================================");
		System.out.println("   Bus Reservation System (BRS) - Phase 1   ");
		System.out.println("  Locale: India | Currency: INR | GST: 5%     ");
		System.out.println("==============================================");
	}

	private Config() {
	}
}