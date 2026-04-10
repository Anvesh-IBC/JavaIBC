package com.ibc.training.shop;

import java.math.BigDecimal;

public class Cart {
	private final int cartId;
	private final Customer customer;
	private OrderItem[] items = new OrderItem[8];
	private int itemCount = 0;

	public Cart(int cartId, Customer customer) {
		if (customer == null)
			throw new IllegalArgumentException("customer required");
		this.cartId = cartId;
		this.customer = customer;
	}

	public int getCartId() {
		return cartId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getItemCount() {
		return itemCount;
	}

	public OrderItem[] getItemsSnapshot() {
		OrderItem[] copy = new OrderItem[itemCount];
		for (int i = 0; i < itemCount; i++)
			copy[i] = items[i];
		return copy;
	}

	public boolean addItem(Product product, int qty) {
		try {
			ensureItemCapacity(itemCount + 1);
			items[itemCount++] = new OrderItem(itemCount, product, qty);
			return true;
		} catch (IllegalArgumentException ex) {
			System.out.println("Cannot add item: " + ex.getMessage());
			return false;
		}
	}

	public boolean removeItem(Product product, int qty) {
		for (int i = 0; i < itemCount; i++) {
			OrderItem oi = items[i];
			if (oi.getProduct().getProductId() == product.getProductId() && oi.getQuantity() == qty) {
				shiftLeftItems(i);
				itemCount--;
				return true;
			}
		}
		return false;
	}

	public BigDecimal subtotal() {
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < itemCount; i++) {
			sum = sum.add(items[i].lineTotal());
		}
		return sum;
	}

	public void clear() {
		for (int i = 0; i < itemCount; i++)
			items[i] = null;
		itemCount = 0;
	}

	/* helpers */
	private void ensureItemCapacity(int needed) {
		if (items.length >= needed)
			return;
		int newCap = Math.max(needed, items.length * 2);
		OrderItem[] bigger = new OrderItem[newCap];
		for (int i = 0; i < itemCount; i++)
			bigger[i] = items[i];
		items = bigger;
	}

	private void shiftLeftItems(int fromIdx) {
		for (int i = fromIdx; i < itemCount - 1; i++) {
			items[i] = items[i + 1];
		}
		items[itemCount - 1] = null;
	}
}