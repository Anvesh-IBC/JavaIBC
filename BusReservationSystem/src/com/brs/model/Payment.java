package com.brs.model;

import com.brs.enums.PaymentMethod;
import com.brs.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
	private long id;
	private long bookingId;
	private double amount;
	private String currency; // "INR"
	private PaymentMethod method;
	private PaymentStatus status;
	private LocalDateTime timestamp;

	public Payment() {
	}

	public Payment(long id, long bookingId, double amount, String currency, PaymentMethod method, PaymentStatus status,
			LocalDateTime timestamp) {
		this.id = id;
		this.bookingId = bookingId;
		this.amount = amount;
		this.currency = currency;
		this.method = method;
		this.status = status;
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public long getBookingId() {
		return bookingId;
	}

	public double getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public PaymentMethod getMethod() {
		return method;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Payment{id=" + id + ", bookingId=" + bookingId + ", amount=" + amount + " " + currency + ", method="
				+ method + ", status=" + status + ", time=" + timestamp + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Payment))
			return false;
		Payment payment = (Payment) o;
		return id == payment.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
