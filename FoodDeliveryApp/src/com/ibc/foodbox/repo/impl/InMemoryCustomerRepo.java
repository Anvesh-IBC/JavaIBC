package com.ibc.foodbox.repo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibc.foodbox.domain.Customer;
import com.ibc.foodbox.repo.CustomerRepository;

public class InMemoryCustomerRepo implements CustomerRepository {
	private Map<Integer, Customer> store = new HashMap<Integer, Customer>();

	public Customer save(Customer c) {
		if (c == null)
			return null;
		store.put(c.getId(), c);
		return c;
	}

	public Customer findById(int id) {
		return store.get(Integer.valueOf(id));
	}

	public List<Customer> findAll() {
		return new ArrayList<Customer>(store.values());
	}
}