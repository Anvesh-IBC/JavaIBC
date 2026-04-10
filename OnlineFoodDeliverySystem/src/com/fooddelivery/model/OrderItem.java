package com.fooddelivery.model;

public class OrderItem {
	private MenuItem item;
	private int quantity;

	public OrderItem(MenuItem item, int quantity) {
		if (quantity <= 0)
			throw new IllegalArgumentException("Quantity must be > 0");
		this.item = item;
		this.quantity = quantity;
	}

	public double getLineTotal() {
		return item.getEffectivePrice() * quantity;
	}

	public MenuItem getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
}
