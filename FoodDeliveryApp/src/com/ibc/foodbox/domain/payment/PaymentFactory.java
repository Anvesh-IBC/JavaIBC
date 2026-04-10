package com.ibc.foodbox.domain.payment;

public final class PaymentFactory {

	private PaymentFactory() {
	}

	public static Payment create(String type, String tokenOrNull) {
		if (type == null)
			return null;
		if ("CASH".equalsIgnoreCase(type))
			return new CashPayment();
		if ("CARD".equalsIgnoreCase(type))
			return new CardPayment(tokenOrNull);
		return null; // caller checks for null and handles it
	}
}