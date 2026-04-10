package com.ibc.foodbox.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibc.foodbox.pricing.DeliveryPricingStrategy;

public class Order {
	private int id;
	private Customer customer;
	private Restaurant restaurant;
	private List<OrderLine> lines;
	private OrderStatus status;
	private Date createdAt;

	public Order() {
		this.lines = new ArrayList<OrderLine>();
		this.status = OrderStatus.CREATED;
		this.createdAt = new Date();
	}

	public Order(int id, Customer c, Restaurant r) {
		this();
		this.id = id;
		this.customer = c;
		this.restaurant = r;
	}

	public int getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public List<OrderLine> getLines() {
		return lines;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void addLine(OrderLine ol) {
		if (ol != null)
			this.lines.add(ol);
	}

	public double itemsTotal() {
		double sum = 0.0;
		for (int i = 0; i < lines.size(); i++) {
			sum += lines.get(i).lineTotal();
		}
		return sum;
	}

	public double grandTotal(DeliveryPricingStrategy strategy, double distanceKm) {
		double delivery = (strategy != null) ? strategy.computeFee(this, distanceKm) : 0.0;
		return itemsTotal() + delivery;
	}

	@Override
	public String toString() {
		String custName = (customer != null ? customer.getName() : "null");
		String restName = (restaurant != null ? restaurant.getName() : "null");
		String st = (status != null ? status.name() : "null");
		return "Order{id=" + id + ",customer=" + custName + ",restaurant=" + restName + ",status=" + st + "}";
	}

}
