package com.bitebuddy.repository;

import com.bitebuddy.domain.MenuItem;
import java.util.*;

public class InMemoryMenuRepository {
	private final Map<Integer, MenuItem> store = new HashMap<Integer, MenuItem>();

	public void saveAll(List<MenuItem> items) {
		for (MenuItem m : items)
			store.put(m.getId(), m);
	}

	public Optional<MenuItem> findById(int id) {
		return Optional.ofNullable(store.get(id));
	}

	public List<MenuItem> findAll() {
		return new ArrayList<MenuItem>(store.values());
	}
}
