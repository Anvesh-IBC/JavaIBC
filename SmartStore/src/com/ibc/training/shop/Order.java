package com.ibc.training.shop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Order {
	private final int orderId;
	private final Customer customer;
	private final Address shippingAddress;

	private OrderItem[] items; // fixed snapshot from Cart
	private int itemCount;

	private BigDecimal price;
	private BigDecimal discountApplied;
	private BigDecimal gst;
	private BigDecimal netTotal;

	private PaymentReceipt receipt; // 1:1
	private OrderStatus status;

	public Order(int orderId, Customer customer, OrderItem[] itemsSnapshot, Address shippingAddress) {
		if (customer == null)
			throw new IllegalArgumentException("customer required");
		if (itemsSnapshot == null || itemsSnapshot.length == 0)
			throw new IllegalArgumentException("order requires items");
		if (shippingAddress == null)
			throw new IllegalArgumentException("shipping address required");

		this.orderId = orderId;
		this.customer = customer;
		this.shippingAddress = shippingAddress;

		// defensive copy
		this.itemCount = itemsSnapshot.length;
		this.items = new OrderItem[itemCount];
		for (int i = 0; i < itemCount; i++)
			this.items[i] = itemsSnapshot[i];

		this.price = subtotal();
		this.discountApplied = BigDecimal.ZERO;
		this.gst = BigDecimal.ZERO;
		this.netTotal = price;
		this.status = OrderStatus.CREATED;
	}

	public static Order fromCart(int orderId, Cart cart, Address shippingAddress) {
		return new Order(orderId, cart.getCustomer(), cart.getItemsSnapshot(), shippingAddress);
	}

	public int getOrderId() {
		return orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public int getItemCount() {
		return itemCount;
	}

	public OrderItem[] getItemsSnapshot() {
		OrderItem[] copy = new OrderItem[itemCount];
		for (int i = 0; i < itemCount; i++)
			copy[i] = items[i];
		return copy;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getDiscountApplied() {
		return discountApplied;
	}

	public BigDecimal getGst() {
		return gst;
	}

	public BigDecimal getNetTotal() {
		return netTotal;
	}

	public PaymentReceipt getReceipt() {
		return receipt;
	}

	public OrderStatus getStatus() {
		return status;
	}

	private BigDecimal subtotal() {
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < itemCount; i++) {
			sum = sum.add(items[i].lineTotal());
		}
		return sum.setScale(2, RoundingMode.HALF_UP);
	}

	public void applyDiscounts(DiscountStrategy[] discounts) {
		BigDecimal running = this.price;
		BigDecimal totalDiscount = BigDecimal.ZERO;

		if (discounts != null) {
			for (int i = 0; i < discounts.length; i++) {
				DiscountStrategy d = discounts[i];
				if (d == null)
					continue;
				BigDecimal before = running;
				running = d.apply(running);
				BigDecimal diff = before.subtract(running);
				if (diff.compareTo(BigDecimal.ZERO) < 0)
					diff = BigDecimal.ZERO;
				totalDiscount = totalDiscount.add(diff);
				System.out.println("Applied " + d.name() + " -> -" + diff + " | running: " + running);
			}
		}
		this.discountApplied = totalDiscount.setScale(2, RoundingMode.HALF_UP);
		this.netTotal = running.setScale(2, RoundingMode.HALF_UP);
	}

	public void applyGst(BigDecimal percent) {
		if (percent == null)
			percent = new BigDecimal("18");
		BigDecimal fraction = percent.divide(new BigDecimal("100"));
		this.gst = netTotal.multiply(fraction).setScale(2, RoundingMode.HALF_UP);
		this.netTotal = netTotal.add(gst).setScale(2, RoundingMode.HALF_UP);
	}

	public void finalizeOrder(Payment payment) {
		if (payment == null)
			throw new IllegalArgumentException("payment required");
		try {
			this.receipt = payment.pay(netTotal);
			this.status = OrderStatus.PAID;
			System.out.println("Order finalized. " + receipt);
		} catch (RuntimeException ex) {
			this.status = OrderStatus.FAILED;
			throw ex;
		}
	}

	public String buildInvoice() {
		StringBuilder sb = new StringBuilder();
		sb.append("===== INVOICE #").append(orderId).append(" [").append(status).append("]").append(" =====\n");
		sb.append("Customer: ").append(customer.getName()).append("\n");
		sb.append("Ship To: ").append(shippingAddress).append("\n");
		sb.append("Items:\n");
		for (int i = 0; i < itemCount; i++) {
			OrderItem oi = items[i];
			sb.append("  - ").append(oi.getProduct().getName()).append(" x ").append(oi.getQuantity()).append(" @ ")
					.append(oi.getProduct().getUnitPrice()).append(" = ").append(oi.lineTotal()).append("\n");
		}
		sb.append("Subtotal: ").append(price).append("\n");
		sb.append("Discount: ").append(discountApplied).append("\n");
		sb.append("GST: ").append(gst).append("\n");
		sb.append("Net Total: ").append(netTotal).append("\n");
		if (receipt != null)
			sb.append("Paid: ").append(receipt).append("\n");
		return sb.toString();
	}
}