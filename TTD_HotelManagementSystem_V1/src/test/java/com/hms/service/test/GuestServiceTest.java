package com.hms.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hms.dao.GuestDAO;
import com.hms.model.Guest;
import com.hms.service.GuestService;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

	@Mock
	private GuestDAO guestDAO;
	@InjectMocks
	private GuestService service;

	@Test
	void addGuest_valid_saves() {
		Guest g = new Guest("G-100", "Prasanna", "9876543210", "p@mail.com");
		service.addGuest(g);
		verify(guestDAO).save(g);
	}

	@Test
	void addGuest_invalidPhone_throws() {
		Guest g = new Guest("G-101", "A", "12", "a@mail.com");
		assertThrows(IllegalArgumentException.class, () -> service.addGuest(g));
		verifyNoInteractions(guestDAO);
	}

	@Test
	void addGuest_invalidEmail_throws() {
		Guest g = new Guest("G-101", "A", "9876543210", "bad");
		assertThrows(IllegalArgumentException.class, () -> service.addGuest(g));
		verifyNoInteractions(guestDAO);
	}

	@Test
	void findAndList_delegateToDAO() {
		Guest g = new Guest("G-1", "N", "9876543210", "a@a.com");
		when(guestDAO.findById("G-1")).thenReturn(Optional.of(g));
		when(guestDAO.findAll()).thenReturn(Collections.singletonList(g));

		assertTrue(service.findById("G-1").isPresent());
		assertEquals(1, service.listAll().size());
	}
}
