package com.hms.tests.junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.hms.domain.Money;
import com.hms.patterns.strategy.WardBilling;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JUnit5FeaturesTest {

	@BeforeAll
	static void beforeAll() {
		/* global init */ }

	@AfterAll
	static void afterAll() {
		/* global cleanup */ }

	@BeforeEach
	void beforeEach() {
		/* per-test setup */ }

	@AfterEach
	void afterEach() {
		/* per-test cleanup */ }

	// Nested (test hierarchies)
	@Nested
	class AssertionsGroup {
		@Test
		@Order(1)
		void groupedAssertions_example() {
			Money m = new Money(new BigDecimal("10.00"), Currency.getInstance("INR"));
			assertAll("money", () -> assertEquals("INR", m.getCurrency().getCurrencyCode()),
					() -> assertEquals(new BigDecimal("10.00"), m.getAmount()));
		}
	}

	// Disabled test example
	@Disabled("Demo: how to disable tests temporarily")
	@Test
	void disabled_example() {
		fail("Should not run");
	}

	// Assumptions
	@Test
	void assumptions_example() {
		Assumptions.assumeTrue(System.getProperty("java.version") != null);
		assertTrue(true); // continues if assumption holds
	}

	// Parameterized: CSV
	@ParameterizedTest
	@CsvSource({ "2000.00, 3, 6000.00", "1500.00, 2, 3000.00" })
	void parameterized_csv_wardBilling(String perDay, int days, String expected) {
		WardBilling strat = new WardBilling();
		Money per = new Money(new BigDecimal(perDay), Currency.getInstance("INR"));
		assertEquals("INR " + expected, strat.compute(per, days).toString());
	}

	// Parameterized: MethodSource
	static Stream<Arguments> moneyAndDays() {
		return Stream.of(
				Arguments.of(new Money(new BigDecimal("100.00"), Currency.getInstance("INR")), 5, "INR 500.00"),
				Arguments.of(new Money(new BigDecimal("50.00"), Currency.getInstance("INR")), 4, "INR 200.00"));
	}

	@ParameterizedTest
	@MethodSource("moneyAndDays")
	void parameterized_methodSource(Money m, int days, String expected) {
		assertEquals(expected, new WardBilling().compute(m, days).toString());
	}

	// Parameterized: argument conversion (String -> LocalDateTime via JUnit
	// built-in)
	@ParameterizedTest
	@ValueSource(strings = { "2030-01-01T00:00:00", "2040-12-31T23:59:59" })
	void parameterized_with_implicit_conversion(LocalDateTime dt) {
		assertTrue(dt.isAfter(LocalDateTime.now())); // should be in the future
	}

	// Repeated tests
	@RepeatedTest(3)
	void repeated_example(RepetitionInfo info) {
		assertTrue(info.getCurrentRepetition() >= 1);
	}

	// Dynamic tests
	@TestFactory
	Collection<DynamicTest> dynamicTests_example() {
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		return data.stream().map(n -> DynamicTest.dynamicTest("isEven? " + n, () -> {
			boolean expected = (n % 2 == 0);
			assertEquals(expected, n % 2 == 0);
		})).collect(Collectors.toList());
	}
}
