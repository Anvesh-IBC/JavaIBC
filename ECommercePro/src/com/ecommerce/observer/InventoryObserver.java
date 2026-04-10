package com.ecommerce.observer;

public class InventoryObserver implements Observer {
	public void update(String message) {
		System.out.println("INVENTORY ALERT:" + message);
	}
}
