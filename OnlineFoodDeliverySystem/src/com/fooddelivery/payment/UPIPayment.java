package com.fooddelivery.payment;

public class UPIPayment implements PaymentGateway {
	@Override
	public boolean pay(double amount) {
		System.out.println("Paid" + " " + amount + " " + "Via UPI");
		return true;
	}

	@Override
	public String getName() {
		return "UPI";
	}
}
