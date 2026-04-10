package com.hms.dao;

import com.hms.model.PaymentRecord;
import java.util.*;

public class InMemoryPaymentDAO implements PaymentDAO {
	private final List<PaymentRecord> store = new ArrayList<>();

	@Override
	public void save(PaymentRecord payment) {
		store.add(payment);
	}

	@Override
	public List<PaymentRecord> findByBooking(String bookingId) {
		List<PaymentRecord> res = new ArrayList<>();
		for (PaymentRecord pr : store) {
			if (bookingId.equals(pr.getBookingId()))
				res.add(pr);
		}
		return res;
	}

	@Override
	public List<PaymentRecord> findAll() {
		return new ArrayList<>(store);
	}
}