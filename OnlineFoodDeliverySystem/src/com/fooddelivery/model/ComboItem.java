package com.fooddelivery.model;

public class ComboItem extends MenuItem {
	private double discountPercent;

	public ComboItem(String name, double price, Category category, double discountPercent) {
		super(name, price, category);
		this.discountPercent = discountPercent;
	}

	@Override
	public double getEffectivePrice() {
		return super.getPrice() * (1 - discountPercent / 100);
	}
}
