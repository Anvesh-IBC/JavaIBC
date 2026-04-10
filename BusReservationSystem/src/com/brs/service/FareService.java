package com.brs.service;

import com.brs.enums.ServiceType;
import com.brs.model.Route;

public class FareService {

	/**
	 * Demonstrates arithmetic/relational/logical/ternary operators and wrapper
	 * usage.
	 */
	public double computeBaseFare(Route route) {
		// Arithmetic
		double base = route.getDistanceKm() * route.getBaseFarePerKm();

		// Relational + logical: ensure positive numbers
		boolean valid = (route.getDistanceKm() > 0) && (route.getBaseFarePerKm() > 0);
		if (!valid)
			throw new IllegalArgumentException("Invalid base fare inputs.");

		return base;
	}

	public double computeSurcharge(ServiceType type, double baseFare) {
		// Ternary operator: 20% surcharge for AC
		double surcharge = (type == ServiceType.AC) ? baseFare * 0.20 : 0.0;
		return surcharge;
	}

	public double computeGST(double amount) {
		return amount * com.brs.util.Config.DEFAULT_GST;
	}

	public double totalFare(Route route, ServiceType type) {
		double base = computeBaseFare(route);
		double surcharge = computeSurcharge(type, base);
		double subtotal = base + surcharge;
		double gst = computeGST(subtotal);
		return subtotal + gst;
	}
}
