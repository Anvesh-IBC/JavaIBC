package com.ibc.foodbox.pricing;

import com.ibc.foodbox.domain.Order;

public class DistanceBasedStrategy implements DeliveryPricingStrategy {
	private double base;
	private double perKm;

	public DistanceBasedStrategy(double base, double perKm) {
		this.base = base;
		this.perKm = perKm;
	}

	public double computeFee(Order order, double distanceKm) {
		return base + (perKm * distanceKm);
	}
}