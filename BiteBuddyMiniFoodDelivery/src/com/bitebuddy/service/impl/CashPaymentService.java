package com.bitebuddy.service.impl;

import com.bitebuddy.domain.Order;
import com.bitebuddy.exceptions.PaymentFailedException;
import com.bitebuddy.service.PaymentService;

public class CashPaymentService implements PaymentService {
	public void process(Order order) throws PaymentFailedException {
		// Simulate COD - nothing to charge
		order.setPaymentRef("CASH-" + order.getId());
	}
}