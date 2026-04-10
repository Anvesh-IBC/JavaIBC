package com.brs.repo;

import com.brs.model.Payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentRepository {
	private final Map<Long, Payment> byId = new HashMap<Long, Payment>();

	public void add(Payment p) {
		if (p == null) {
			throw new IllegalArgumentException("Payment cannot be null.");
		}
		byId.put(p.getId(), p);
	}

	/**
	 * Java 7: Returns Payment or null instead of Optional.
	 */
	public Payment find(long id) {
		return byId.get(id);
	}

	/**
	 * Returns all payments as a new List.
	 */
	public List<Payment> list() {
		return new ArrayList<Payment>(byId.values());
	}
}