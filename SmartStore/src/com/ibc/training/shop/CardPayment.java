package com.ibc.training.shop;

import java.math.BigDecimal;
import java.util.Date;

public class CardPayment implements Payment {
	private final String maskedCard;

	public CardPayment(String maskedCard) {
		this.maskedCard = maskedCard;
	}

	public PaymentReceipt pay(BigDecimal amount) {
		if (amount == null)
			amount = BigDecimal.ZERO;
		BigDecimal finalAmount = amount.add(CONVENIENCE_FEE);
		System.out.println("[CARD] Paying " + finalAmount + " with card: " + maskedCard);
		return new PaymentReceipt(IdGen.nextId(), PaymentMethod.CARD, finalAmount, new Date());
	}
}