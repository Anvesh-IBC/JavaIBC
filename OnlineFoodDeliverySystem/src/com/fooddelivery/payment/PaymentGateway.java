package com.fooddelivery.payment;

public interface PaymentGateway {
	boolean pay(double amount);

	String getName();

	default void printReceiptHeader() {
		System.out.println("==== Payment Receipt ====");
	}

	static boolean validateAmount(double amount) {
		return amount > 0;
	}
}
