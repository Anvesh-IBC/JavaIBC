package com.bitebuddy.service.impl;

import com.bitebuddy.domain.Order;
import com.bitebuddy.exceptions.PaymentFailedException;
import com.bitebuddy.service.PaymentService;

public class CardPaymentService implements PaymentService {
	public void process(Order order) throws PaymentFailedException {
		// Simulate success
		order.setPaymentRef("CARD-OK-" + order.getId());
	}
}