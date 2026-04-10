package com.ibc.foodbox.app;

import java.util.List;
import java.util.Scanner;

import com.ibc.foodbox.config.AppConfig;
import com.ibc.foodbox.domain.*;
import com.ibc.foodbox.domain.payment.*;
import com.ibc.foodbox.pricing.*;
import com.ibc.foodbox.repo.CustomerRepository;
import com.ibc.foodbox.repo.OrderRepository;
import com.ibc.foodbox.repo.RestaurantRepository;
import com.ibc.foodbox.util.IdGenerator;

public class ConsoleApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AppConfig cfg = AppConfig.getInstance();

		seedData(cfg);

		System.out.println("=== FoodBox (Java 7 Console) ===");
		boolean run = true;
		while (run) {
			menu();
			int ch = readInt(sc, "Choose (1-7): ");
			try {
				switch (ch) {
				case 1:
					listRestaurants(cfg);
					break;
				case 2:
					placeOrder(sc, cfg);
					break;
				case 3:
					listOrders(cfg);
					break;
				case 4:
					switchToDistancePricing(sc, cfg);
					break;
				case 5:
					demoCollectionsAndSorting();
					break;
				case 6:
					demoArraysAndOperators();
					break;
				case 7:
					run = false;
					System.out.println("Bye!");
					break;
				default:
					System.out.println("Invalid option.");
				}
			} catch (Throwable t) {
				System.out
						.println("Error: " + (t.getMessage() == null ? t.getClass().getSimpleName() : t.getMessage()));
				// TEMP: print stack trace during debugging to know exact line/file
				t.printStackTrace();
			}
		}
		sc.close();
	}

	private static void menu() {
		System.out.println("\n1) List Restaurants & Menu");
		System.out.println("2) Place Order");
		System.out.println("3) List Orders");
		System.out.println("4) Switch to Distance-based Pricing");
		System.out.println("5) Demo: Collections & Sorting");
		System.out.println("6) Demo: Arrays & Operators");
		System.out.println("7) Exit");
	}

	private static int readInt(Scanner sc, String prompt) {
		while (true) {
			System.out.print(prompt);
			String s = sc.nextLine();
			try {
				return Integer.parseInt(s.trim());
			} catch (NumberFormatException e) {
				System.out.println("Enter an integer.");
			}
		}
	}

	private static double readDouble(Scanner sc, String prompt) {
		while (true) {
			System.out.print(prompt);
			String s = sc.nextLine();
			try {
				return Double.parseDouble(s.trim());
			} catch (NumberFormatException e) {
				System.out.println("Enter a number.");
			}
		}
	}

	private static void seedData(AppConfig cfg) {
		RestaurantRepository rr = cfg.restaurants();

		Category indian = new Category(1, "Indian");
		Category pizza = new Category(2, "Pizza");

		Restaurant r1 = new Restaurant(1, "Tasty Bites");
		MenuItem mi1 = new MenuItem(1, "Paneer Butter Masala", 240);
		mi1.addCategory(indian);
		r1.addMenuItem(mi1);
		MenuItem mi2 = new MenuItem(2, "Butter Naan", 40);
		mi2.addCategory(indian);
		r1.addMenuItem(mi2);

		Restaurant r2 = new Restaurant(2, "PizzaVille");
		MenuItem mi3 = new MenuItem(3, "Margherita", 299);
		mi3.addCategory(pizza);
		r2.addMenuItem(mi3);
		MenuItem mi4 = new MenuItem(4, "Veggie Supreme", 399);
		mi4.addCategory(pizza);
		r2.addMenuItem(mi4);

		rr.save(r1);
		rr.save(r2);

		CustomerRepository cr = cfg.customers();
		Customer c1 = new Customer(1, "Jyothi", new Address("MG Road", "Bengaluru", "560001"));
		cr.save(c1);
	}

	private static void listRestaurants(AppConfig cfg) {
		List<Restaurant> all = cfg.restaurants().findAll();
		if (all == null || all.isEmpty()) {
			System.out.println("(No restaurants)");
			return;
		}
		for (int i = 0; i < all.size(); i++) {
			Restaurant r = all.get(i);
			System.out.println(r.getId() + ") " + r.getName());
			List<MenuItem> menu = r.getMenu();
			for (int j = 0; j < menu.size(); j++) {
				MenuItem m = menu.get(j);
				System.out.println("   - " + m.getId() + " " + m.getName() + " : Rs" + m.getPrice());
			}
		}
	}

	// ===== FIXED METHOD (Complete) =====
	private static void placeOrder(Scanner sc, AppConfig cfg) {
		CustomerRepository cr = cfg.customers();
		RestaurantRepository rr = cfg.restaurants();
		OrderRepository or = cfg.orders();

		Customer cust = cr.findById(1);
		if (cust == null) {
			System.out.println("Customer not found.");
			return;
		}

		listRestaurants(cfg);

		int rid = readInt(sc, "Choose Restaurant ID: ");
		Restaurant r = rr.findById(rid);
		if (r == null) {
			System.out.println("Restaurant not found.");
			return;
		}

		Order order = new Order(IdGenerator.nextId(), cust, r);

		boolean addMore = true;
		while (addMore) {
			int itemId = readInt(sc, "MenuItem ID to add: ");
			MenuItem chosen = null;
			List<MenuItem> menu = r.getMenu();
			for (int i = 0; i < menu.size(); i++) {
				if (menu.get(i).getId() == itemId) {
					chosen = menu.get(i);
					break;
				}
			}
			if (chosen == null) {
				System.out.println("Invalid item.");
			} else {
				int qty = readInt(sc, "Quantity: ");
				if (qty <= 0)
					qty = 1;
				order.addLine(new OrderLine(chosen, qty));
			}
			System.out.print("Add more? (y/n): ");
			String ans = sc.nextLine();
			addMore = "y".equalsIgnoreCase(ans != null ? ans.trim() : "");
		}

		double distance = readDouble(sc, "Distance (km) for delivery: ");
		if (distance < 0)
			distance = 0;

		double total = order.grandTotal(cfg.pricingStrategy(), distance);

		double discount = (total >= 500.0) ? 50.0 : 0.0;
		total = total - discount;

		System.out.println("Items total: Rs" + order.itemsTotal());
		System.out.println("Discount: Rs" + discount);
		System.out.println("Grand total (with delivery): Rs" + total);

		System.out.print("Payment type (CASH/CARD): ");
		String ptype = sc.nextLine();

		Payment payment = "CARD".equalsIgnoreCase(ptype) ? PaymentFactory.create("CARD", "*-*-**-1234")
				: PaymentFactory.create("CASH", null);

		if (payment == null) {
			System.out.println("Unsupported payment type.");
			return;
		}

		if (payment.pay(total)) {
			order.setStatus(OrderStatus.PAID);
			or.save(order);
			cust.addOrder(order);

			// ---- Defensive printing to avoid NPE and to pinpoint issues
			String methodSafe;
			try {
				methodSafe = String.valueOf(payment.method());
			} catch (Throwable t) {
				methodSafe = "(method threw: " + t.getClass().getSimpleName() + ")";
			}

			String orderSafe;
			try {
				orderSafe = String.valueOf(order); // calls toString()
			} catch (Throwable t) {
				orderSafe = "(order.toString threw: " + t.getClass().getSimpleName() + ")";
			}

			System.out.println("Paid via " + methodSafe + ". Order placed: " + orderSafe);
		} else {
			System.out.println("Payment failed.");
		}
	}
	// ===== END FIXED METHOD =====

	private static void listOrders(AppConfig cfg) {
		List<Order> all = cfg.orders().findAll();
		if (all == null || all.isEmpty()) {
			System.out.println("(No orders yet)");
			return;
		}
		for (int i = 0; i < all.size(); i++) {
			Order o = all.get(i);
			System.out.println(o + " | Items=" + o.getLines().size() + " | ItemsTotal=Rs" + o.itemsTotal());
		}
	}

	private static void switchToDistancePricing(Scanner sc, AppConfig cfg) {
		double base = readDouble(sc, "Base fee: ");
		double perKm = readDouble(sc, "Per-km fee: ");
		// NOTE: If your class is spelled 'DistanceBasedStratergy' keep it consistent,
		// but ideally rename to 'DistanceBasedStrategy'
		cfg.setPricingStrategy(new DistanceBasedStrategy(base, perKm));
		System.out.println("Pricing strategy switched.");
	}

	private static void demoCollectionsAndSorting() {
		System.out.println("\n[Collections Demo]");
		java.util.List<String> list = new java.util.ArrayList<String>();
		list.add("Banana");
		list.add("Apple");
		list.add("Cherry");

		System.out.println("Original: " + list);
		java.util.Collections.sort(list);
		System.out.println("Sorted (natural): " + list);

		java.util.Set<Integer> set = new java.util.HashSet<Integer>();
		set.add(3);
		set.add(1);
		set.add(2);
		set.add(2);
		System.out.println("HashSet (no duplicates, no order): " + set);

		java.util.Map<String, Integer> map = new java.util.HashMap<String, Integer>();
		map.put("a", 10);
		map.put("b", 20);
		map.put("c", 15);
		System.out.println("HashMap: " + map + " value for 'b'=" + map.get("b"));

		java.util.TreeSet<Integer> treeSet = new java.util.TreeSet<Integer>();
		treeSet.add(10);
		treeSet.add(5);
		treeSet.add(20);
		System.out.println("TreeSet: " + treeSet + " floor(12)=" + treeSet.floor(12));

		java.util.TreeMap<String, Integer> treeMap = new java.util.TreeMap<String, Integer>();
		treeMap.put("mango", 5);
		treeMap.put("apple", 2);
		treeMap.put("orange", 7);
		System.out.println(
				"TreeMap firstKey=" + treeMap.firstKey() + ", ceilingKey('banana')=" + treeMap.ceilingKey("banana"));
	}

	private static void demoArraysAndOperators() {
		System.out.println("\n[Arrays & Operators Demo]");

		int[] nums = new int[3];
		nums[0] = 10;
		nums[1] = 20;
		nums[2] = 30;

		String[] fruits = new String[] { "apple", "banana", "cherry" };

		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
		}
		boolean cond = (sum > 30) && (nums[0] == 10);
		String result = cond ? "OK" : "NOT OK";

		System.out.println("Sum=" + sum + ", cond=" + cond + ", result=" + result);
		System.out.println("fruits[1]=" + fruits[1]);

		Integer boxed = nums[0];
		int primitive = boxed;
		System.out.println("boxed=" + boxed + ", primitive=" + primitive);

		String s = "Hello";
		s = s.concat(" World");
		StringBuilder sb = new StringBuilder();
		sb.append("Hello").append(" World");
		System.out.println("String=" + s + ", StringBuilder=" + sb.toString());
	}
}