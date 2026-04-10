package com.brs.model;

import java.util.Objects;

/** Base class for vehicles */

public class Vehicle {
	private long id;
	private String plateNumber;

	public Vehicle() {
	}

	public Vehicle(long id, String plateNumber) {
		this.id = id;
		this.plateNumber = plateNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Vehicle))
			return false;
		Vehicle vehicle = (Vehicle) o;
		return id == vehicle.id && Objects.equals(plateNumber, vehicle.plateNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, plateNumber);
	}

	@Override
	public String toString() {
		return "Vehicle{id=" + id + ", plate='" + plateNumber + "'}";
	}
}