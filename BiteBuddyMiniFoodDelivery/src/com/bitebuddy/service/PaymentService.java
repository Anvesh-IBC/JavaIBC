package com.bitebuddy.service;

import com.bitebuddy.domain.Order;
import com.bitebuddy.exceptions.PaymentFailedException;

public interface PaymentService {
	void process(Order order) throws PaymentFailedException;
}