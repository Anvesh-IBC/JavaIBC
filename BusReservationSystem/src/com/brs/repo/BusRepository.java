package com.brs.repo;

import com.brs.model.Bus;

import java.util.*;

public class BusRepository {
	private final Map<Long, Bus> buses = new HashMap<>();

	public void add(Bus bus) {
		buses.put(bus.getId(), bus);
	}

	public Optional<Bus> find(long id) {
		return Optional.ofNullable(buses.get(id));
	}

	public List<Bus> list() {
		return new ArrayList<>(buses.values());
	}

	public SortedSet<Integer> sortedCapacities() {
		SortedSet<Integer> set = new TreeSet<>();
		for (Bus b : buses.values())
			set.add(b.getCapacity());
		return set; // TreeSet demo
	}
}
