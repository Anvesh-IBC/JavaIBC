package com.ibc.training.shop;

import java.math.BigDecimal;

public class OrderItem {
	private final int itemId;
	private final Product product;
	private final int quantity;

	public OrderItem(int itemId, Product product, int quantity) {
		if (product == null)
			throw new IllegalArgumentException("product required");
		if (quantity <= 0)
			throw new IllegalArgumentException("quantity must be > 0");
		if (quantity > product.getStock())
			throw new IllegalArgumentException("quantity exceeds stock");
		this.itemId = itemId;
		this.product = product;
		this.quantity = quantity;
	}

	public int getItemId() {
		return itemId;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal lineTotal() {
		return product.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
	}

	@Override
	public String toString() {
		return product.getName() + " x " + quantity + " = " + lineTotal();
	}
}