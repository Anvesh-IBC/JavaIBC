package com.ecommerce.model;

import com.ecommerce.observer.Observer;

import com.ecommerce.service.*;

public class Customer extends Entity implements Purchasable, Orderable, Reportable, Observable {
	private LineItem[] cart = new LineItem[10];
	private int cartCount = 0;
	private double total;
	private Observer[] observers = new Observer[5];
	private int observerCount = 0;

	public Customer(String id) {
		super(id);
	}

	public String getType() {
		return "Cusomer";
	}

	public void addToCart(Product p, int qty) {
		cart[cartCount++] = new LineItem(p, qty);
		total += p.getPrice() * qty;
		notifyObservers("Added" + " " + qty + " " + "of" + " " + p.getId());
	}

	public void processOrder() {
		for (int i = 0; i < cartCount; i++) {
			cart[i].getProduct().updateStock(-cart[i].getQuantity());
		}
		notifyObservers("Order processed. Total = " + total);
		cartCount = 0;
		total = 0;
	}

	public void addObserver(Observer o) {
		observers[observerCount++] = o;
	}

	private void notifyObservers(String msg) {
		for (int i = 0; i < observerCount; i++) {
			observers[i].update(msg);
		}
	}

	public String generateReport() {
		return "Customer" + entityId + "|Items:" + cartCount + "|Total" + total;
	}
}
