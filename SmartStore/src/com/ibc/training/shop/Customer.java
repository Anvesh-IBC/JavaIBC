package com.ibc.training.shop;

public class Customer {
	private final int customerId;
	private final String name;
	private final String email;
	private final String phone;
	private final Address defaultAddress;

	private Cart currentCart; // 1:1
	private Order[] orders = new Order[4]; // 1:M
	private int orderCount = 0;

	public Customer(int customerId, String name, String email, String phone, Address defaultAddress) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.defaultAddress = defaultAddress;
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Address getDefaultAddress() {
		return defaultAddress;
	}

	public Cart getCurrentCart() {
		return currentCart;
	}

	public void setCurrentCart(Cart cart) {
		this.currentCart = cart;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public Order[] getOrdersSnapshot() {
		Order[] copy = new Order[orderCount];
		for (int i = 0; i < orderCount; i++)
			copy[i] = orders[i];
		return copy;
	}

	public void addOrder(Order order) {
		if (order == null)
			return;
		ensureOrderCapacity(orderCount + 1);
		orders[orderCount++] = order;
	}

	private void ensureOrderCapacity(int needed) {
		if (orders.length >= needed)
			return;
		int newCap = Math.max(needed, orders.length * 2);
		Order[] bigger = new Order[newCap];
		for (int i = 0; i < orderCount; i++)
			bigger[i] = orders[i];
		orders = bigger;
	}

	@Override
	public String toString() {
		return "Customer{" + name + ", id=" + customerId + "}";
	}
}