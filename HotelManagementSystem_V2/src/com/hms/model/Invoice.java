package com.hms.model;

import java.time.LocalDateTime;

public class Invoice {
	private String invoiceNo;
	private String bookingId;
	private double total;
	private Currency currency;
	private LocalDateTime createdAt;

	public Invoice() {
	}

	public Invoice(String invoiceNo, String bookingId, double total, Currency currency, LocalDateTime createdAt) {
		this.invoiceNo = invoiceNo;
		this.bookingId = bookingId;
		this.total = total;
		this.currency = currency;
		this.createdAt = createdAt;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Invoice{" + "invoiceNo='" + invoiceNo + '\'' + ", bookingId='" + bookingId + '\'' + ", total=" + total
				+ ", currency=" + currency + ", createdAt=" + createdAt + '}';
	}
}
