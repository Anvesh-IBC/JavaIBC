package com.hms.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Money {
	private final BigDecimal amount;
	private final Currency currency;

	public Money(BigDecimal amount, Currency currency) {
		if (amount == null || currency == null)
			throw new IllegalArgumentException("Null Money parts");
		this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Money add(Money other) {
		ensureSameCurrency(other);
		return new Money(amount.add(other.amount), currency);
	}

	public Money multiply(int qty) {
		return new Money(amount.multiply(BigDecimal.valueOf(qty)), currency);
	}

	private void ensureSameCurrency(Money other) {
		if (!currency.equals(other.currency))
			throw new IllegalArgumentException("Currency mismatch");
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Money && ((Money) o).amount.equals(amount) && ((Money) o).currency.equals(currency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, currency);
	}

	@Override
	public String toString() {
		return currency.getCurrencyCode() + " " + amount;
	}
}
