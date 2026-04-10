package com.fooddelivery.model;

import java.util.Date;

public class Order {
	private static int nextId = 1;
	private int orderId;
	private Date createdAt;
	private OrderItem[] items;
	private String couponCode;

	{
		createdAt = new Date();
	}

	public Order(OrderItem[] items, String couponCode) {
		this.orderId = nextId++;
		this.items = items;
		this.couponCode = couponCode;
	}

	public int getOrderId() {
		return orderId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public OrderItem[] getItems() {
		return items;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public double calculateSubtotal() {
		double subtotal = 0;
		for (OrderItem oi : items)
			subtotal += oi.getLineTotal();
		return subtotal;
	}
}
