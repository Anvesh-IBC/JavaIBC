package com.ibc.training.shop;

import java.math.BigDecimal;

public interface Payment {
	BigDecimal CONVENIENCE_FEE = new BigDecimal("0.00");

	PaymentReceipt pay(BigDecimal amount);
}