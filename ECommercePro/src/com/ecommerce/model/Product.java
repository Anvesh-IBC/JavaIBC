package com.ecommerce.model;

import com.ecommerce.enums.Category;

public class Product {
	private final String id;
	private final Category category;
	private final double price;
	private int stock;

	public Product(String id, Category cat, double price, int stock) {
		this.id = id;
		this.category = cat;
		this.price = price;
		this.stock = stock;
	}

	public String getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public void updateStock(int delta) {
		stock += delta;
	}
}
