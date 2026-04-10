package com.bitebuddy.app;

import com.bitebuddy.bootstrap.Bootstrap;
import com.bitebuddy.domain.*;
import com.bitebuddy.exceptions.InvalidMenuItemException;
import com.bitebuddy.exceptions.PaymentFailedException;
import com.bitebuddy.facade.OrderFacade;
import com.bitebuddy.repository.InMemoryOrderRepository;
import com.bitebuddy.service.CartService;
import com.bitebuddy.service.MenuService;
import com.bitebuddy.service.OrderService;
import com.bitebuddy.service.impl.*;
import com.bitebuddy.util.MoneyUtil;
import com.bitebuddy.util.ReceiptPrinter;
import com.bitebuddy.util.Validators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MainApp {
	private static final Scanner SC = new Scanner(System.in);

	public static void main(String[] args) {
		// Initialize services
		MenuService menuService = new SimpleMenuService(Bootstrap.MENU_REPO);
		CartService cartService = new SimpleCartService();
		InMemoryOrderRepository orderRepo = new InMemoryOrderRepository();
		OrderService orderService = new SimpleOrderService(orderRepo, Bootstrap.CONFIG);
		SimpleRiderAssignmentService riderService = new SimpleRiderAssignmentService();
		OrderFacade facade = new OrderFacade(orderService, riderService);

		// State
		List<CartItem> cart = new ArrayList<CartItem>();
		Customer sessionCustomer = new Customer(1L, "Anvesh", "9876543210", "BTM Layout");

		boolean running = true;
		while (running) {
			System.out.println("\n=== BiteBuddy (Java 8) ===");
			System.out.println("1) Browse Menu");
			System.out.println("2) Search Item");
			System.out.println("3) Add to Cart");
			System.out.println("4) View Cart");
			System.out.println("5) Remove from Cart");
			System.out.println("6) Checkout");
			System.out.println("7) Order History (today)");
			System.out.println("0) Exit");
			System.out.print("Choose: ");
			String choice = SC.nextLine().trim();

			switch (choice) {
			case "1":
				List<MenuItem> all = menuService.getAllItems();
				for (MenuItem m : all) {
					System.out.println(m.getId() + "  " + m.getName() + "  " + MoneyUtil.inr(m.getPrice()) + "  ["
							+ (m.isVeg() ? "Veg" : "Non-veg") + "]");
				}
				break;
			case "2":
				System.out.print("Search text: ");
				String q = SC.nextLine();
				List<MenuItem> results = menuService.findByName(q);
				if (results.isEmpty())
					System.out.println("No items found");
				results.forEach(mi -> System.out
						.println(mi.getId() + "  " + mi.getName() + "  " + MoneyUtil.inr(mi.getPrice())));
				break;
			case "3":
				try {
					System.out.print("Enter Menu Item ID: ");
					int id = Integer.parseInt(SC.nextLine());
					MenuItem item = menuService.findById(id).orElseThrow(() -> new InvalidMenuItemException(id));
					System.out.print("Quantity (1..20): ");
					int qty = Integer.parseInt(SC.nextLine());
					Validators.requireQty(qty);
					cartService.addItem(cart, item, qty);
					System.out.println("Added to cart: " + item.getName() + " x " + qty);
				} catch (Exception ex) {
					System.out.println("Error: " + ex.getMessage());
				}
				break;
			case "4":
				showCart(cart, cartService);
				break;
			case "5":
				System.out.print("Enter Item ID to remove: ");
				try {
					int rid = Integer.parseInt(SC.nextLine());
					cartService.removeItem(cart, rid);
					System.out.println("Removed (if existed).");
				} catch (NumberFormatException e) {
					System.out.println("Invalid number.");
				}
				break;
			case "6":
				if (cart.isEmpty()) {
					System.out.println("Cart empty.");
					break;
				}
				System.out.print("Payment (CASH/CARD/UPI): ");
				String pm = SC.nextLine().trim().toUpperCase();
				PaymentMethod method;
				try {
					method = PaymentMethod.valueOf(pm);
				} catch (Exception e) {
					System.out.println("Invalid payment method.");
					break;
				}
				try {
					Order order = facade.checkout(sessionCustomer, cart, method);
					Rider r = facade.assignRider();
					// Set status and print receipt
					order.setStatus(OrderStatus.CONFIRMED);
					ReceiptPrinter.writeReceipt(order, r);
					System.out
							.println("Order created: " + order.getId() + " Total: " + MoneyUtil.inr(order.getTotal()));
					System.out.println("Receipt saved under output/receipts/");
					cart.clear(); // eligible for GC when not referenced
				} catch (PaymentFailedException e) {
					System.out.println("Payment failed: " + e.getMessage());
				}
				break;
			case "7":
				LocalDate today = LocalDate.now();
				List<Order> todays = orderService.getOrdersBetween(today.atStartOfDay(), today.atTime(23, 59, 59));
				if (todays.isEmpty()) {
					System.out.println("No orders today.");
				} else {
					System.out.println("Today's Orders:");
					for (Order o : todays)
						System.out.println(o);
					// Export CSV
					String path = "output/reports/orders_" + today.toString() + ".csv";
					ReceiptPrinter.exportDaily(todays, path);
					System.out.println("Exported CSV: " + path);
				}
				break;
			case "0":
				running = false;
				System.out.println("Goodbye!");
				break;
			default:
				System.out.println("Invalid choice.");
				break;
			}
		}
		
		// --- Java 8 Pass-by-Value demo (conceptual) ---
        // int x; System.out.println(x); // Uninitialized local variable won't compile (demo)
        // void tryToChangePrimitive(int a) { a = 99; } // primitive remains unchanged at caller
        // void tryToChangeList(List<String> names) { names.add("Added"); } // list mutation visible at caller
		
	}
	
	private static void showCart(List<CartItem> cart, CartService cartService) {
	    if (cart.isEmpty()) {
	        System.out.println("Cart is empty.");
	        return;
	    }

	    System.out.println("\n--- Cart ---");

	    BigDecimal subtotal = BigDecimal.ZERO;

	    for (CartItem ci : cart) {
	        BigDecimal lineTotal = ci.getItem()
	                .getPrice()
	                .multiply(BigDecimal.valueOf(ci.getQuantity()));

	        subtotal = subtotal.add(lineTotal);

	        System.out.println(
	                ci.getItem().getId() + "  "
	                + ci.getItem().getName() + " x "
	                + ci.getQuantity() + " = "
	                + MoneyUtil.inr(lineTotal)
	        );
	    }

	    System.out.println("-----------------");
	    System.out.println("Subtotal: " + MoneyUtil.inr(subtotal));
	}

	
}
