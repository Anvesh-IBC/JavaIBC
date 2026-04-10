package com.hms.util.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.hms.util.Validators;

class ValidatorsTest {

	@Test
	void phoneValidation() {
		assertTrue(Validators.isValidPhone("9876543210"));
		assertFalse(Validators.isValidPhone("98765"));
		assertFalse(Validators.isValidPhone(null));
	}

	@Test
	void emailValidation() {
		assertTrue(Validators.isValidEmail("a.b+1@mail.com"));
		assertFalse(Validators.isValidEmail("bad@"));
		assertFalse(Validators.isValidEmail(null));
	}
}
