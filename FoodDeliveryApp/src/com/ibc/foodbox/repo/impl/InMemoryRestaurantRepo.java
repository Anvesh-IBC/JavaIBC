package com.ibc.foodbox.repo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibc.foodbox.domain.Restaurant;
import com.ibc.foodbox.repo.RestaurantRepository;

public class InMemoryRestaurantRepo implements RestaurantRepository {
	private Map<Integer, Restaurant> store = new HashMap<Integer, Restaurant>();

	public Restaurant save(Restaurant r) {
		if (r == null)
			return null;
		store.put(r.getId(), r);
		return r;
	}

	public Restaurant findById(int id) {
		return store.get(Integer.valueOf(id));
	}

	public List<Restaurant> findAll() {
		return new ArrayList<Restaurant>(store.values());
	}
}