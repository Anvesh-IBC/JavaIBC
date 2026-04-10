package com.brs.model;

import com.brs.enums.ServiceType;

import java.util.Objects;

/**
 * Bus extends Vehicle; demonstrates instance initialization block: defaults
 * capacity to 40 unless overridden by constructor/setter.
 */

public class Bus extends Vehicle {
	private int capacity;
	private ServiceType serviceType;

	// Instance initialization block (runs before constructors)
	{
		capacity = 40;
	}

	public Bus() {
	}

	public Bus(long id, String plateNumber, ServiceType serviceType, Integer capacityOverride) {
		super(id, plateNumber);
		this.serviceType = serviceType;
		if (capacityOverride != null)
			this.capacity = capacityOverride; // Wrapper -> autounboxing demo
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	@Override
	public String toString() {
		return "Bus{id=" + getId() + ", plate='" + getPlateNumber() + "', type=" + serviceType + ", capacity="
				+ capacity + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Bus))
			return false;
		Bus bus = (Bus) o;
		return getId() == bus.getId() && capacity == bus.capacity && serviceType == bus.serviceType
				&& Objects.equals(getPlateNumber(), bus.getPlateNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getPlateNumber(), capacity, serviceType);
	}
}