package com.brs.service;

import com.brs.model.Trip;
import com.brs.repo.TripRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NavigableMap;

/**
 * CatalogService (Java 7 compatible) - Uses java.util.Date instead of
 * java.time.LocalDate - No streams/lambdas; manual aggregation and anonymous
 * Comparators - No Optional; returns Date or null from
 * nextAvailableDate/floorDate
 */
public class CatalogService {
	private final TripRepository tripRepo;

	public CatalogService(TripRepository tripRepo) {
		this.tripRepo = tripRepo;
	}

	/**
	 * Lists all trips sorted by date, then by departure time (String "HH:mm").
	 * Returns an unmodifiable view.
	 */
	public List<Trip> listAllSortedByDate() {
		List<Trip> trips = tripRepo.listAll();

		// Sort by Trip.getDate() then Trip.getDeparture()
		Collections.sort(trips, new Comparator<Trip>() {
			@Override
			public int compare(Trip t1, Trip t2) {
				// Compare dates (Date implements Comparable)
				int cmpDate = safeCompareDates(t1.getDate(), t2.getDate());
				if (cmpDate != 0)
					return cmpDate;

				// Then compare departure strings (lexicographic works for "HH:mm")
				String d1 = t1.getDeparture();
				String d2 = t2.getDeparture();
				if (d1 == null && d2 == null)
					return 0;
				if (d1 == null)
					return -1;
				if (d2 == null)
					return 1;
				return d1.compareTo(d2);
			}
		});

		return Collections.unmodifiableList(trips);
	}

	/**
	 * Searches trips within [from, to] range (inclusive), sorted by date then
	 * departure.
	 */
	public List<Trip> searchByDateRange(Date from, Date to) {
		NavigableMap<Date, List<Trip>> window = tripRepo.subMap(from, to);

		// Aggregate all lists into a single result list (no streams in Java 7)
		List<Trip> result = new ArrayList<Trip>();
		for (List<Trip> dayList : window.values()) {
			if (dayList != null) {
				result.addAll(dayList);
			}
		}

		// Sort result by date then departure
		Collections.sort(result, new Comparator<Trip>() {
			@Override
			public int compare(Trip t1, Trip t2) {
				int cmpDate = safeCompareDates(t1.getDate(), t2.getDate());
				if (cmpDate != 0)
					return cmpDate;
				String d1 = t1.getDeparture();
				String d2 = t2.getDeparture();
				if (d1 == null && d2 == null)
					return 0;
				if (d1 == null)
					return -1;
				if (d2 == null)
					return 1;
				return d1.compareTo(d2);
			}
		});

		return result;
	}

	/**
	 * Returns the next available date strictly greater than 'current', or null if
	 * none.
	 */
	public Date nextAvailableDate(Date current) {
		return tripRepo.nextDate(current);
	}

	/**
	 * Returns the greatest date less than or equal to 'current', or null if none.
	 */
	public Date floorDate(Date current) {
		return tripRepo.floorDate(current);
	}

	// --------- Helpers ---------

	private int safeCompareDates(Date d1, Date d2) {
		if (d1 == null && d2 == null)
			return 0;
		if (d1 == null)
			return -1;
		if (d2 == null)
			return 1;
		return d1.compareTo(d2);
	}
}
