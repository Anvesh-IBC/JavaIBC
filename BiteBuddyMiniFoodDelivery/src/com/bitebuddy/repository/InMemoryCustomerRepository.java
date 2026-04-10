package com.bitebuddy.repository;

import com.bitebuddy.domain.Customer;
import java.util.*;

public class InMemoryCustomerRepository implements Repository<Customer, Long> {
	private final Map<Long, Customer> store = new HashMap<Long, Customer>();

	@Override
	public Customer save(Customer entity) {
		store.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public List<Customer> findAll() {
		return new ArrayList<Customer>(store.values());
	}

	@Override
	public void deleteById(Long id) {
		store.remove(id);
	}
}