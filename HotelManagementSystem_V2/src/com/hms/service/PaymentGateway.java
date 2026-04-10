package com.hms.service;

public class PaymentGateway {

	public String charge(String customerId, double amount) {
		try {
			Thread.sleep(200); // simulate network call
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return "PAY-" + System.currentTimeMillis();
	}
}
