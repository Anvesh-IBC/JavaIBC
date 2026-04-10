package com.ibc.training.shop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageDiscount implements DiscountStrategy {
	private final BigDecimal percent;
	private final String name;

	public PercentageDiscount(double percent, String name) {
		if (percent < 0.0 || percent > 100.0)
			throw new IllegalArgumentException("percent must be 0..100");
		this.percent = BigDecimal.valueOf(percent);
		this.name = (name == null ? "PercentageDiscount" : name);
	}

	public String name() {
		return name;
	}

	public BigDecimal apply(BigDecimal total) {
		if (total == null)
			total = BigDecimal.ZERO;
		BigDecimal discount = total.multiply(percent).divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);
		BigDecimal out = total.subtract(discount);
		return out.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : out.setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public String toString() {
		return name + " (" + percent + "%)";
	}
}