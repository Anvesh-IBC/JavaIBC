package com.bitebuddy.util;

import com.bitebuddy.domain.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public final class ReceiptPrinter {
	private ReceiptPrinter() {
	}

	public static void writeReceipt(Order o, Rider rider) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== BiteBuddy Receipt ===\n");
		sb.append("Order: ").append(o.getId()).append("  Date: ").append(o.getCreatedAt().format(DateUtil.RECEIPT_FMT))
				.append('\n');
		sb.append("Customer: ").append(o.getCustomer().getName()).append('\n');
		sb.append("Address: ").append(o.getCustomer().getAddress()).append('\n');
		sb.append("Payment Ref: ").append(o.getPaymentRef()).append('\n');
		sb.append("Status: ").append(o.getStatus()).append('\n');

		sb.append("\nItems:\n");
		for (CartItem ci : o.getItems()) {
			BigDecimal line = ci.getItem().getPrice().multiply(new BigDecimal(ci.getQuantity()));
			sb.append("  ").append(ci.getItem().getName()).append(" x ").append(ci.getQuantity()).append(" = ")
					.append(MoneyUtil.inr(line)).append('\n');
		}
		sb.append("\nSubtotal       : ").append(MoneyUtil.inr(o.getSubtotal())).append('\n');
		sb.append("Tax            : ").append(MoneyUtil.inr(o.getTax())).append('\n');
		sb.append("Delivery Fee   : ").append(MoneyUtil.inr(o.getDeliveryFee())).append('\n');
		sb.append("Discount       : ").append(MoneyUtil.inr(o.getDiscount())).append('\n');
		sb.append("-------------------------------------\n");
		sb.append("Total          : ").append(MoneyUtil.inr(o.getTotal())).append('\n');

		if (rider != null) {
			sb.append("Assigned Rider : ").append(rider.getName()).append(" (Area: ").append(rider.getServiceArea())
					.append(")\n");
		}

		writeToFile("output/receipts/order_" + o.getId() + ".txt", sb.toString());
	}

	public static void exportDaily(java.util.List<Order> orders, String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("orderId,createdAt,total\n");
		for (Order o : orders) {
			sb.append(o.getId()).append(",").append(o.getCreatedAt()).append(",").append(o.getTotal()).append("\n");
		}
		writeToFile(path, sb.toString());
	}

	private static void writeToFile(String pathStr, String data) {
		try {
			Path path = Paths.get(pathStr);
			Files.createDirectories(path.getParent());
			try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING)) {
				bw.write(data);
			}
		} catch (IOException e) {
			System.err.println("Failed to write file: " + e.getMessage());
		}
	}
}
