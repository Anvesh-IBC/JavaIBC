package com.ibc.training.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class FormattingUtil {
	private FormattingUtil() {
	}

	public static String formatCurrency(BigDecimal amount, Locale locale) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		return nf.format(amount);
	}

	public static String formatDate(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
	}
}