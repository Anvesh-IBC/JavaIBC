package com.ibc.foodbox.domain.payment;

public final class CardPayment implements Payment {

	private final String maskedCard; // can be null; not used in method()

	public CardPayment(String maskedCard) {
		this.maskedCard = maskedCard;
	}

	public boolean pay(double amount) {
		// Simulate card charge success
		return true;
	}

	public String method() {
		// Keep this simple and null-safe
		return "CARD";
	}

	@Override
	public String toString() {
		return "CardPayment(" + String.valueOf(maskedCard) + ")";
	}
}