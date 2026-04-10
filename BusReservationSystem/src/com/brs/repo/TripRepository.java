package com.brs.repo;

import com.brs.model.Trip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.HashMap;

/**
 * Maintains trips and a TreeMap index by date for fast range navigation (Java
 * 7).
 */
public class TripRepository {
	private final Map<Long, Trip> trips = new HashMap<Long, Trip>();
	private final TreeMap<Date, List<Trip>> dateIndex = new TreeMap<Date, List<Trip>>();

	public void add(Trip trip) {
		if (trip == null) {
			throw new IllegalArgumentException("Trip cannot be null.");
		}
		trips.put(trip.getId(), trip);

		Date key = trip.getDate(); // Trip.getDate() should return a defensive copy
		List<Trip> list = dateIndex.get(key);
		if (list == null) {
			list = new ArrayList<Trip>();
			dateIndex.put(key, list);
		}
		list.add(trip);
	}

	/**
	 * Java 7: Returns Trip or null instead of Optional.
	 */
	public Trip find(long id) {
		return trips.get(id);
	}

	public List<Trip> listAll() {
		return new ArrayList<Trip>(trips.values());
	}

	public NavigableMap<Date, List<Trip>> subMap(Date from, Date to) {
		return dateIndex.subMap(from, true, to, true);
	}

	/**
	 * Java 7: Returns next date (or null) using TreeMap#higherKey.
	 */
	public Date nextDate(Date date) {
		return dateIndex.higherKey(date);
	}

	/**
	 * Java 7: Returns floor date (or null) using TreeMap#floorKey.
	 */
	public Date floorDate(Date date) {
		return dateIndex.floorKey(date);
	}
}