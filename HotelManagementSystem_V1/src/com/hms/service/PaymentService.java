package com.hms.service;

import com.hms.dao.PaymentDAO;
import com.hms.model.PaymentMode;
import com.hms.model.PaymentRecord;
import com.hms.util.Ids;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class PaymentService {
	private final PaymentDAO paymentDAO;

	public PaymentService(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}

	public PaymentRecord pay(String bookingId, PaymentMode mode, double amount) {
		if (amount <= 0)
			throw new IllegalArgumentException("Invalid amount");
		String id = Ids.paymentId();
		PaymentRecord pr = new PaymentRecord(id, bookingId, mode, amount, LocalDateTime.now());
		paymentDAO.save(pr);
		return pr;
	}

	public double totalPaid(String bookingId) {
		return paymentDAO.findByBooking(bookingId).stream().mapToDouble(PaymentRecord::getAmount).sum();
	}

	public Map<PaymentMode, Double> revenueByMode() {
		return paymentDAO.findAll().stream().collect(
				Collectors.groupingBy(PaymentRecord::getMode, Collectors.summingDouble(PaymentRecord::getAmount)));
	}
}
