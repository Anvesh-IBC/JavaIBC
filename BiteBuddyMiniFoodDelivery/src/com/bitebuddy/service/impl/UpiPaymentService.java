package com.bitebuddy.service.impl;

import com.bitebuddy.domain.Order;
import com.bitebuddy.exceptions.PaymentFailedException;
import com.bitebuddy.service.PaymentService;

public class UpiPaymentService implements PaymentService {
	public void process(Order order) throws PaymentFailedException {
		order.setPaymentRef("UPI-OK-" + order.getId());
	}
}
