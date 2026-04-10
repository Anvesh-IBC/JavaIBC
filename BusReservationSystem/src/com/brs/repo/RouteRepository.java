package com.brs.repo;

import com.brs.model.Route;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RouteRepository {
	private final Map<String, Route> routesByCode = new HashMap<String, Route>();

	public void upsert(Route route) {
		if (route == null || route.getCode() == null) {
			throw new IllegalArgumentException("Route and route code must not be null.");
		}
		routesByCode.put(route.getCode(), route);
	}

	/**
	 * Java 7: return Route or null (instead of Optional).
	 */
	public Route findByCode(String code) {
		return routesByCode.get(code);
	}

	/**
	 * Returns a new Set to demonstrate Set semantics (unique elements).
	 */
	public Set<Route> getAllUnique() {
		return new HashSet<Route>(routesByCode.values());
	}
}
