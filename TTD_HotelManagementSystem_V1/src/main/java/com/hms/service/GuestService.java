package com.hms.service;

import com.hms.dao.GuestDAO;
import com.hms.model.Guest;
import com.hms.util.Validators;

import java.util.List;
import java.util.Optional;

public class GuestService {
	private final GuestDAO guestDAO;

	public GuestService(GuestDAO guestDAO) {
		this.guestDAO = guestDAO;
	}

	public void addGuest(Guest guest) {
		if (!Validators.isValidPhone(guest.getPhone()))
			throw new IllegalArgumentException("Invalid phone");
		if (!Validators.isValidEmail(guest.getEmail()))
			throw new IllegalArgumentException("Invalid email");
		guestDAO.save(guest);
	}

	public Optional<Guest> findById(String guestId) {
		return guestDAO.findById(guestId);
	}

	public List<Guest> listAll() {
		return guestDAO.findAll();
	}
}
