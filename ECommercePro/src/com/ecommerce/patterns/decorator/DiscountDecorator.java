package com.ecommerce.patterns.decorator;

import com.ecommerce.service.Reportable;

public class DiscountDecorator implements Reportable {
	private Reportable base;
	private double discount;

	public DiscountDecorator(Reportable base, double discount) {
		this.base = base;
		this.discount = discount;
	}

	public String generateReport() {
		return base.generateReport() + "|Discount:" + (discount * 100) + "%";
	}
}
