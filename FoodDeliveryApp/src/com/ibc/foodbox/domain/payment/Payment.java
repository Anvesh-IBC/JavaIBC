package com.ibc.foodbox.domain.payment;

public interface Payment {
	 boolean pay(double amount);
	 String method();
}
