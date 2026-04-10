package com.bitebuddy.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class MoneyUtil {
	private MoneyUtil() {
	}

	public static String inr(BigDecimal amt) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		return nf.format(amt);
	}
}