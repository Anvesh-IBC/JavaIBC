package com.ibc.training.shop;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {

		// --- Setup ---
		Address home = new Address("221B Baker Street", "Bengaluru", "KA", "560001");
		Customer cust = new Customer(101, "Rohit", "rohit@example.com", "+91-9999999999", home);

		// One-to-One: active cart
		Cart cart = new Cart(1, cust);
		cust.setCurrentCart(cart);

		// Categories (Many-to-Many with Product)
		Category electronics = new Category(1, "ELECTRONICS");
		Category fashion = new Category(2, "FASHION");

		// Products (is-a hierarchy)
		Product laptop = new PhysicalProduct(1, "Laptop", new BigDecimal("60000.00"), 10, 1.5);
		Product mouse = new PhysicalProduct(2, "Mouse", new BigDecimal("800.00"), 50, 0.1);
		Product ebook = new DigitalProduct(3, "Java eBook", new BigDecimal("499.00"), 1000,
				"https://example.com/ebook");

		// Link products to categories (M:N)
		laptop.addCategory(electronics);
		mouse.addCategory(electronics);
		ebook.addCategory(electronics);
		ebook.addCategory(fashion);

		// Cart items (1:M)
		cart.addItem(laptop, 1);
		cart.addItem(mouse, 2);
		cart.addItem(ebook, 3);

		System.out.println("Cart subtotal: " + cart.subtotal());

		// Create order from cart; provide shipping snapshot
		Address shipTo = new Address("42 MG Road", "Bengaluru", "KA", "560002");
		Order order = Order.fromCart(1001, cart, shipTo);

		// Track history (Customer -> Orders)
		cust.addOrder(order);

		// Discounts as array (Strategy)
		DiscountStrategy[] discounts = new DiscountStrategy[2];
		discounts[0] = new PercentageDiscount(10.0, "Festival 10%");
		discounts[1] = new FlatDiscount(new BigDecimal("300.00"), "Instant Flat 300");

		order.applyDiscounts(discounts);

		// GST
		order.applyGst(new BigDecimal("18"));

		// Payment (DIP + Polymorphism)
		Payment payment = new UpiPayment("rohit@upi"); // or: new CardPayment("XXXX-XXXX-XXXX-1234");

		System.out.println("Order status before pay: " + order.getStatus());
		order.finalizeOrder(payment);
		System.out.println("Order status after pay: " + order.getStatus());

		// Invoice
		System.out.println(order.buildInvoice());
	}
}