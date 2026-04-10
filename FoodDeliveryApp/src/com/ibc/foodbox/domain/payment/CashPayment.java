package com.ibc.foodbox.domain.payment;

public final class CashPayment implements Payment {

	public CashPayment() {
	}

	public boolean pay(double amount) {
		// Simulate success
		return true;
	}

	public String method() {
		return "CASH";
	}

	@Override
	public String toString() {
		return "CashPayment";
	}
}
