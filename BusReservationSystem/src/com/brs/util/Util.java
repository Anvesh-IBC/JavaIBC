package com.brs.util;

import java.time.LocalDate;
import java.time.LocalTime;

public final class Util {
	private Util() {
	}

	public static String formatCurrency(double amount) {
		return Config.INR.format(amount);
	}

	public static String formatSchedule(LocalDate date, LocalTime dep, LocalTime arr) {
		return String.format("%s | Dep %s | Arr %s", date, dep, arr);
	}
}