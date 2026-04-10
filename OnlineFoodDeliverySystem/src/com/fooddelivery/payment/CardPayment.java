package com.fooddelivery.payment;

public class CardPayment implements PaymentGateway {
	@Override
	public boolean pay(double amount) {
		System.out.println("Paid" + " " + amount + " " + "Via Card(****123456)");
		return true;
	}

	@Override
	public String getName() {
		return "Card";
	}
}
