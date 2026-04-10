package com.hms.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.hms.util.Dates;

class DatesTest {

	@Test
	void parseAndFormat_iso() {
		LocalDate d = Dates.parse("2024-08-15");
		assertEquals("2024-08-15", Dates.format(d));
	}
}
