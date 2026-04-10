package com.example.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringCalculatorTest {

	private final StringCalculator calc = new StringCalculator();

	@Test
	@DisplayName("Null or empty string returns zero")
	void nullOrEmptyReturnsZero() {
		assertEquals(0, calc.add(null));
		assertEquals(0, calc.add(""));
		assertEquals(0, calc.add(" "));
	}

	@Test
	@DisplayName("Valid comma-separated tokens are summed")
	void sumsValidTokens() {
		assertEquals(6, calc.add("1,2,3"));
		assertEquals(7, calc.add("10, -5, 2"));
		assertEquals(42, calc.add(" 40, 2 "));
	}

	@ParameterizedTest(name = "add(\"{0}\") = {1}")
	@CsvSource(value = { "1;1", "1,2,3;6", "0,0,0;0", "5,-2,1;4", "10,-5,2;7", " 10 ,  -5 , 2 ;7" }, delimiter = ';')
	void parameterizedSums(String input, int expected) {
		assertEquals(expected, calc.add(input));
	}

	@Test
	@DisplayName("Invalid tokens cause IllegalArgumentException")
	void invalidTokenThrows() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> calc.add("a,2"));
		assertTrue(ex.getMessage().contains("Invalid number"));
	}
}