package com.fooddelivery.model;

import java.text.NumberFormat;
import java.util.Locale;

public class MenuItem {
	private String name;
	private double price;
	private Category category;

	public MenuItem(String name, double price, Category category) {
		this.name = name;
		this.price = price;
		this.category = category;

	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public Category getCategory() {
		return category;
	}

	public double getEffectivePrice() {
		return price;
	}

	public String formatPrice() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		return nf.format(getEffectivePrice());
	}
}
