package com.brs.model;

import com.brs.enums.ServiceType;

import java.util.Objects;

/**
 * Route with literals: distanceKm/baseFarePerKm (double), serviceType (enum).
 * Demonstrates equals/hashCode for Sets/Maps.
 */
public class Route {
	private String code;
	private String origin;
	private String destination;
	private double distanceKm;
	private double baseFarePerKm;
	private ServiceType serviceType;

	public Route() {
	}

	public Route(String code, String origin, String destination, double distanceKm, double baseFarePerKm,
			ServiceType serviceType) {
		if (distanceKm <= 0 || baseFarePerKm <= 0) {
			throw new IllegalArgumentException("Distance and base fare must be positive.");
		}
		this.code = code;
		this.origin = origin;
		this.destination = destination;
		this.distanceKm = distanceKm;
		this.baseFarePerKm = baseFarePerKm;
		this.serviceType = serviceType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getDistanceKm() {
		return distanceKm;
	}

	public void setDistanceKm(double distanceKm) {
		this.distanceKm = distanceKm;
	}

	public double getBaseFarePerKm() {
		return baseFarePerKm;
	}

	public void setBaseFarePerKm(double baseFarePerKm) {
		this.baseFarePerKm = baseFarePerKm;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	@Override
	public String toString() {
		return "Route{" + code + " " + origin + "->" + destination + ", dist=" + distanceKm + "km, baseFare="
				+ baseFarePerKm + ", svc=" + serviceType + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Route))
			return false;
		Route route = (Route) o;
		return Objects.equals(code, route.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}
}
