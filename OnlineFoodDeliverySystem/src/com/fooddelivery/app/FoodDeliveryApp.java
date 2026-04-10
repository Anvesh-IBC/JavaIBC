package com.fooddelivery.app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.payment.CardPayment;
import com.fooddelivery.payment.PaymentGateway;
import com.fooddelivery.payment.UPIPayment;
import com.fooddelivery.util.Catalog;

public class FoodDeliveryApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nWelcome to Online Food Delivery System");	
		System.out.println("\n==== MENU LIST ====\n");
		for (int i = 0; i < Catalog.MENU.length; i++) {
			MenuItem mi = Catalog.MENU[i];
			System.out.println((i + 1) + ". " + mi.getName() + "(" + mi.getCategory() + ")-" + mi.formatPrice());
		}
		System.out.println("\nNote: Please choose your menu item and quantity (e.g., Menu Item 2 with quantity 2 -> (2-2)).");
		System.out.println("Enter selections (e.g.,1-2,3-1):");
		String input = sc.nextLine();
		Pattern p = Pattern.compile("(\\d+)-(\\d+)");
		Matcher m = p.matcher(input);
		List<OrderItem> orderItems = new ArrayList<>();
		while (m.find() && orderItems.size() < 10) {
			int id = Integer.parseInt(m.group(1));
			int qty = Integer.parseInt(m.group(2));
			//System.out.println(id + " " + qty);
			if (id >= 1 && id <= Catalog.MENU.length && qty > 0) {
				orderItems.add(new OrderItem(Catalog.MENU[id - 1], qty));
			} else {
				System.err.println("Invalid token: " + m.group() +". Please choose a menu item between 1 and 4 only.");
			}
		
		} 
		OrderItem[] itemsArr = orderItems.toArray(new OrderItem[0]);
		System.out.println("Note: Please enter the discount code if you want (e.g., Disc10); otherwise, enter ‘None’.");
		System.out.println("Enter coupon (DISC<n> or NONE):");
		String coupon = sc.nextLine();
		Order order = new Order(itemsArr, coupon);
		double subtotal = order.calculateSubtotal();
		double couponDiscount = 0;
		if (coupon.startsWith("DISC")) {
			try {
				int perc = Integer.parseInt(coupon.substring(4));
				couponDiscount = Math.min(subtotal * perc / 100, 50);
			} catch (Exception e) {
				couponDiscount = 0;
			}
		}
		double extraDiscount = subtotal > 500 ? 50 : 0;
		double deliveryFee = subtotal >= 700 ? 0 : 40;
		double total = subtotal - couponDiscount - extraDiscount + deliveryFee;

		System.out.println("Choose payment method (UPI/CARD):");
		String method = sc.nextLine().toUpperCase();
		PaymentGateway pg = method.equals("CARD") ? new CardPayment() : new UPIPayment();
		if (!PaymentGateway.validateAmount(total)) {
			System.out.println("Invalid amount");
			return;
		}
		pg.printReceiptHeader();
		pg.pay(total);

		StringBuilder sb = new StringBuilder();
		sb.append("Order ID: " + order.getOrderId() + "\nDate: " + order.getCreatedAt() + "\n\nItems:\n");
		for (OrderItem oi : itemsArr) {
			sb.append(oi.getItem().getName() + " x" + oi.getQuantity() + " = " + oi.getLineTotal() + "\n");
		}
		sb.append("Subtotal: " + subtotal + "\nCoupon Discount: " + couponDiscount + "\nExtra Discount: "
				+ extraDiscount + "\nDelivery Fee: " + deliveryFee + "\nTOTAL: " + total + "\n");

		StringBuffer log = new StringBuffer("Payment done via " + pg.getName());
		try (PrintWriter pw = new PrintWriter(new FileWriter("receipt.txt"))) {
			pw.println(sb.toString());
			pw.println(log.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Receipt generated: receipt.txt");
	}
}
