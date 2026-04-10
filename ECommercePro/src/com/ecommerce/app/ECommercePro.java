package com.ecommerce.app;

import com.ecommerce.enums.Category;
import com.ecommerce.factory.EcommerceFactory;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Product;
import com.ecommerce.observer.InventoryObserver;
import com.ecommerce.patterns.decorator.DiscountDecorator;
import com.ecommerce.service.Reportable;

public class ECommercePro {
	public static void main(String[] args) {
		EcommerceFactory factory = new EcommerceFactory();

		Customer customer = (Customer) factory.create("Customer", "CUST01");
		Product phone = (Product) factory.create("Product", "P01", Category.ELECTRONICS, 50000.0, 10);
		InventoryObserver inv = new InventoryObserver();
		customer.addObserver(inv);
		customer.addToCart(phone, 2);
		Reportable discounted = new DiscountDecorator(customer, 0.10);

		System.out.println(discounted.generateReport());

		customer.processOrder();

		System.out.println("ECommercePro executed successfully");
	}
}
