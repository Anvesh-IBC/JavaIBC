package com.ibc.foodbox.domain;

public class OrderLine {
	private MenuItem item;
	private int quantity;

	public OrderLine() {
	}

	public OrderLine(MenuItem item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double lineTotal() {
		return (item != null ? item.getPrice() : 0.0) * quantity;
	}

}
