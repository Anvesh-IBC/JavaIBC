package com.hms.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RatesCatalog {

	private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

	private final Map<String, Double> baseByRoomType = new HashMap<>();
	private final Map<LocalDate, Double> seasonalMultiplier = new HashMap<>();

	public RatesCatalog() {
		baseByRoomType.put("STANDARD", 3000.0);
		baseByRoomType.put("DELUXE", 4000.0);
		baseByRoomType.put("SUITE", 5000.0);
	}

	public double price(String roomType, LocalDate date) {
		rwLock.readLock().lock();
		try {
			double base = baseByRoomType.getOrDefault(roomType, 3000.0);
			double multiplier = seasonalMultiplier.getOrDefault(date, 1.0);
			return base * multiplier;
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public void setBasePrice(String roomType, double price) {
		rwLock.writeLock().lock();
		try {
			baseByRoomType.put(roomType, price);
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	public void setSeasonalMultiplier(LocalDate date, double multiplier) {
		rwLock.writeLock().lock();
		try {
			seasonalMultiplier.put(date, multiplier);
		} finally {
			rwLock.writeLock().unlock();
		}
	}
}
