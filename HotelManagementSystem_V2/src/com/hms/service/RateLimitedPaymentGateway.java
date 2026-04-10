package com.hms.service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RateLimitedPaymentGateway extends PaymentGateway {

	private final Semaphore permits = new Semaphore(5); // max 5 concurrent calls

	@Override
	public String charge(String customerId, double amount) {

		boolean acquired = false;
		try {
			// wait max 500 ms for a slot
			acquired = permits.tryAcquire(500, TimeUnit.MILLISECONDS);

			if (!acquired) {
				throw new RuntimeException("Payment system busy. Try again later.");
			}

			// call real gateway
			return super.charge(customerId, amount);

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Payment interrupted");

		} finally {
			if (acquired) {
				permits.release();
			}
		}
	}
}
