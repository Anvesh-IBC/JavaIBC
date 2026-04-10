package com.hms.dao;

import com.hms.model.Guest;
import java.util.*;

public class InMemoryGuestDAO implements GuestDAO {
	private final Map<String, Guest> store = new HashMap<>();

	@Override
	public void save(Guest guest) {
		store.put(guest.getGuestId(), guest);
	}

	@Override
	public Optional<Guest> findById(String guestId) {
		return Optional.ofNullable(store.get(guestId));
	}

	@Override
	public List<Guest> findAll() {
		return new ArrayList<>(store.values());
	}
}