package com.ibc.training.shop;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentReceipt {
	private final int receiptId;
	private final PaymentMethod method;
	private final BigDecimal amountPaid;
	private final Date paidAt;

	public PaymentReceipt(int receiptId, PaymentMethod method, BigDecimal amountPaid, Date paidAt) {
		this.receiptId = receiptId;
		this.method = method;
		this.amountPaid = amountPaid;
		this.paidAt = paidAt;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public PaymentMethod getMethod() {
		return method;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public Date getPaidAt() {
		return paidAt;
	}

	@Override
	public String toString() {
		return "Receipt#" + receiptId + " [" + method + "] " + amountPaid + " @ " + paidAt;
	}
}