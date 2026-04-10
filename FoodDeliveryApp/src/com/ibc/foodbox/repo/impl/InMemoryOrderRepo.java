package com.ibc.foodbox.repo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibc.foodbox.domain.Order;
import com.ibc.foodbox.repo.OrderRepository;

public class InMemoryOrderRepo implements OrderRepository {
	private Map<Integer, Order> store = new HashMap<Integer, Order>();

	public Order save(Order o) {
		if (o == null)
			return null;
		store.put(o.getId(), o);
		return o;
	}

	public Order findById(int id) {
		return store.get(Integer.valueOf(id));
	}

	public List<Order> findAll() {
		return new ArrayList<Order>(store.values());
	}
}