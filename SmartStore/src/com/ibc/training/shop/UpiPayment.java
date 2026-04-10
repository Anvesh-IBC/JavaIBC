package com.ibc.training.shop;

import java.math.BigDecimal;
import java.util.Date;

public class UpiPayment implements Payment {
	private final String upiId;

	public UpiPayment(String upiId) {
		this.upiId = upiId;
	}

	public PaymentReceipt pay(BigDecimal amount) {
		if (amount == null)
			amount = BigDecimal.ZERO;
		BigDecimal finalAmount = amount.add(CONVENIENCE_FEE);
		System.out.println("[UPI] Paying " + finalAmount + " via UPI ID: " + upiId);
		return new PaymentReceipt(IdGen.nextId(), PaymentMethod.UPI, finalAmount, new Date());
	}
}