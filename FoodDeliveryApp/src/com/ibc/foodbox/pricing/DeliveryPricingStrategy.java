package com.ibc.foodbox.pricing;

import com.ibc.foodbox.domain.Order;

public interface DeliveryPricingStrategy {
	double computeFee(Order order, double distanceKm);
}
