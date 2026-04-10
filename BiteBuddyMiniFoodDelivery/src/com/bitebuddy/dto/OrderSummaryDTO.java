package com.bitebuddy.dto;

import java.math.BigDecimal;

public class OrderSummaryDTO {
	private long orderId;
	private int itemCount;
	private BigDecimal subtotal, tax, discount, total;

	public OrderSummaryDTO(long orderId, int itemCount, BigDecimal subtotal, BigDecimal tax, BigDecimal discount,
			BigDecimal total) {
		this.orderId = orderId;
		this.itemCount = itemCount;
		this.subtotal = subtotal;
		this.tax = tax;
		this.discount = discount;
		this.total = total;
	}

	public long getOrderId() {
		return orderId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public BigDecimal getTotal() {
		return total;
	}
}
