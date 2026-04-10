package com.ibc.training.shop;

import java.math.BigDecimal;

public class FlatDiscount implements DiscountStrategy {
	private final BigDecimal amount;
	private final String name;

	public FlatDiscount(BigDecimal amount, String name) {
		if (amount == null)
			throw new IllegalArgumentException("amount required");
		if (amount.compareTo(BigDecimal.ZERO) < 0)
			throw new IllegalArgumentException("amount cannot be negative");
		this.amount = amount;
		this.name = (name == null ? "FlatDiscount" : name);
	}

	public String name() {
		return name;
	}

	public BigDecimal apply(BigDecimal total) {
		if (total == null)
			total = BigDecimal.ZERO;
		BigDecimal out = total.subtract(amount);
		return out.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : out;
	}

	@Override
	public String toString() {
		return name + " (-" + amount + ")";
	}
}