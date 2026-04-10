package com.bitebuddy.repository;

import com.bitebuddy.domain.Order;
import java.util.*;

public class InMemoryOrderRepository implements Repository<Order, Long> {
	private final Map<Long, Order> store = new HashMap<Long, Order>();

	@Override
	public Order save(Order entity) {
		store.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public Optional<Order> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public List<Order> findAll() {
		return new ArrayList<Order>(store.values());
	}

	@Override
	public void deleteById(Long id) {
		store.remove(id);
	}
}
