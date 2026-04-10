package com.hms.model;

import java.time.LocalDateTime;

public class PaymentRecord {
	private String paymentId;
	private String bookingId;
	private PaymentMode mode;
	private double amount;
	private LocalDateTime paidAt;

	public PaymentRecord() {
	}

	public PaymentRecord(String paymentId, String bookingId, PaymentMode mode, double amount, LocalDateTime paidAt) {
		this.paymentId = paymentId;
		this.bookingId = bookingId;
		this.mode = mode;
		this.amount = amount;
		this.paidAt = paidAt;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public PaymentMode getMode() {
		return mode;
	}

	public void setMode(PaymentMode mode) {
		this.mode = mode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	@Override
	public String toString() {
		return "PaymentRecord{" + "paymentId='" + paymentId + '\'' + ", bookingId='" + bookingId + '\'' + ", mode="
				+ mode + ", amount=" + amount + ", paidAt=" + paidAt + '}';
	}
}
