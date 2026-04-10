package com.ibc.training.shop;

import java.math.BigDecimal;

public class PhysicalProduct extends Product {
	private final double weightKg;

	public PhysicalProduct(int id, String name, BigDecimal price, int stock, double weightKg) {
		super(id, name, price, stock);
		this.weightKg = weightKg;
	}

	public double getWeightKg() {
		return weightKg;
	}
}