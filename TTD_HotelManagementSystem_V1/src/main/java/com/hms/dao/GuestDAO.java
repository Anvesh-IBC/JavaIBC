package com.hms.dao;

import com.hms.model.Guest;
import java.util.List;
import java.util.Optional;

public interface GuestDAO {
	void save(Guest guest);

	Optional<Guest> findById(String guestId);

	List<Guest> findAll();
}
