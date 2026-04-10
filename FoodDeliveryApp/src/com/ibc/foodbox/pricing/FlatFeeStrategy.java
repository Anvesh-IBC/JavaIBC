package com.ibc.foodbox.pricing;

import com.ibc.foodbox.domain.Order;

public class FlatFeeStrategy implements DeliveryPricingStrategy {
	private double flatFee;

	public FlatFeeStrategy(double flatFee) {
		this.flatFee = flatFee;
	}

	public double computeFee(Order order, double distanceKm) {
		return flatFee;
	}
}
