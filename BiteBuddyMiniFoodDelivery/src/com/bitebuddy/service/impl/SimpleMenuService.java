package com.bitebuddy.service.impl;

import com.bitebuddy.domain.MenuItem;
import com.bitebuddy.repository.InMemoryMenuRepository;
import com.bitebuddy.service.MenuService;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleMenuService implements MenuService {
	private final InMemoryMenuRepository repo;

	public SimpleMenuService(InMemoryMenuRepository repo) {
		this.repo = repo;
	}

	public List<MenuItem> getAllItems() {
		List<MenuItem> items = repo.findAll();
		items.sort(Comparator.comparing(MenuItem::getId));
		return items;
	}

	public Optional<MenuItem> findById(int id) {
		return repo.findById(id);
	}

	public List<MenuItem> findByName(String query) {
		final String q = query == null ? "" : query.toLowerCase();
		return repo.findAll().stream().filter(m -> m.getName().toLowerCase().contains(q))
				.sorted(Comparator.comparing(MenuItem::getName)).collect(Collectors.toList());
	}

	public List<MenuItem> findVeg(boolean veg) {
		return repo.findAll().stream().filter(m -> m.isVeg() == veg).sorted(Comparator.comparing(MenuItem::getName))
				.collect(Collectors.toList());
	}
}
