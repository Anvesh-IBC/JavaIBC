package com.ibc.foodbox.domain;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int id;
	private String name;
	private Address address; // 1-1
	private List<Order> orders; // 1-many

	public Customer() {
		this.orders = new ArrayList<Order>();
	}

	public Customer(int id, String name, Address address) {
		this();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void addOrder(Order order) {
		if (order != null)
			this.orders.add(order);
	}

	@Override
	public String toString() {
		return "Customer {id=" + id + ", name=" + name + ", address=" + address + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Customer))
			return false;
		return id == ((Customer) o).id;
	}

	@Override
	public int hashCode() {
		return id;
	}

}
