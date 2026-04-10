package com.brs.model;

import com.brs.enums.ServiceType;

import java.io.Serializable;
import java.util.Date;

/**
 * Trip (Java 7 compatible) - Uses java.util.Date for the calendar date. - Uses
 * String for departure/arrival times ("HH:mm") to avoid java.time. -
 * Initializes Seat[] explicitly; guards against invalid indices. - Defensive
 * copy for Date to preserve encapsulation.
 */
public class Trip implements Serializable {
	private long id;
	private Route route;
	private Bus bus;
	private Date date; // Java 7: mutable, so we clone on get/set/ctor
	private String departure; // "HH:mm"
	private String arrival; // "HH:mm"
	private boolean active;
	private Seat[] seats;

	public Trip() {
		// default constructor
	}

	public Trip(long id, Route route, Bus bus, Date date, String departure, String arrival) {
		if (route == null)
			throw new IllegalArgumentException("Route required.");
		if (bus == null)
			throw new IllegalArgumentException("Bus required.");
		if (date == null)
			throw new IllegalArgumentException("Date required.");
		if (departure == null || arrival == null)
			throw new IllegalArgumentException("Times required.");

		this.id = id;
		this.route = route;
		this.bus = bus;
		// Defensive copy of Date
		this.date = (Date) date.clone();
		this.departure = departure;
		this.arrival = arrival;
		this.active = true;

		// Declare + construct + init Seat[] (avoiding null dereference)
		int capacity = bus.getCapacity();
		this.seats = new Seat[capacity];
		for (int i = 0; i < capacity; i++) {
			seats[i] = new Seat(i);
		}
	}

	// Getters
	public long getId() {
		return id;
	}

	public Route getRoute() {
		return route;
	}

	public Bus getBus() {
		return bus;
	}

	/** Returns a defensive copy since Date is mutable */
	public Date getDate() {
		return date != null ? (Date) date.clone() : null;
	}

	public String getDeparture() {
		return departure;
	}

	public String getArrival() {
		return arrival;
	}

	public boolean isActive() {
		return active;
	}

	// Setters
	public void setActive(boolean active) {
		this.active = active;
	}

	/** Replace date with a defensive copy to maintain encapsulation */
	public void setDate(Date date) {
		this.date = date != null ? (Date) date.clone() : null;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Seat[] getSeats() {
		return seats;
	}

	public Seat getSeat(int index) {
		if (index < 0 || index >= seats.length) {
			throw new IllegalArgumentException("Invalid seat index: " + index);
		}
		return seats[index];
	}

	public int countReserved() {
		int count = 0;
		for (int i = 0; i < seats.length; i++) {
			if (seats[i].isReserved())
				count++;
		}
		return count;
	}

	public int countFree() {
		return seats.length - countReserved();
	}

	/** Trip service type driven by the Bus */
	public ServiceType getServiceType() {
		return bus != null ? bus.getServiceType() : null;
	}

	@Override
	public String toString() {
		return "Trip{id=" + id + ", route=" + (route != null ? route.getCode() : "null") + ", bus="
				+ (bus != null ? bus.getPlateNumber() : "null") + ", date=" + date + ", dep=" + departure + ", arr="
				+ arrival + ", active=" + active + ", freeSeats=" + countFree() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Trip))
			return false;
		Trip trip = (Trip) o;
		return id == trip.id;
	}

	@Override
	public int hashCode() {
		// Manual hashCode for Java 7 (no Objects.hash)
		return (int) (id ^ (id >>> 32));
	}
}