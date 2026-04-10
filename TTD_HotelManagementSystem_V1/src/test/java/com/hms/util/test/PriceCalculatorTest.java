package com.hms.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.hms.model.RoomType;
import com.hms.util.PriceCalculator;

class PriceCalculatorTest {

	@Test
	void computeNightly_addsTypeUplift() {
		assertEquals(3000, PriceCalculator.computeNightly(RoomType.STANDARD, 3000), 0.0001);
		assertEquals(4000, PriceCalculator.computeNightly(RoomType.DELUXE, 3000), 0.0001);
		assertEquals(5000, PriceCalculator.computeNightly(RoomType.SUITE, 3000), 0.0001);
	}

	@Test
	void computeTax_andDiscount_andTotal() {
		double nightly = 4000;
		int days = 8; // eligible for 10% discount
		double subtotal = nightly * days; // 32000
		assertEquals(0.12 * subtotal, PriceCalculator.computeTax(subtotal), 0.0001);
		assertEquals(0.10, PriceCalculator.computeDiscount(days), 0.0001);
		double total = PriceCalculator.computeTotal(nightly, days);
		assertEquals(subtotal + 0.12 * subtotal - 0.10 * subtotal, total, 0.0001);
	}
}