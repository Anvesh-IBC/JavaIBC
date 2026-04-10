package com.hms.domain.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Test;

import com.hms.domain.Money;

class MoneyTest {

	Money inr(String amt) {
		return new Money(new BigDecimal(amt), Currency.getInstance("INR"));
	}

	@Test
	void add_sameCurrency_ok() {
		Money a = inr("10.00");
		Money b = inr("2.50");
		assertEquals(inr("12.50"), a.add(b));
	}

	@Test
	void multiply_integer_ok() {
		Money a = inr("3.33");
		assertEquals(inr("9.99"), a.multiply(3));
		assertEquals("INR 9.99", a.multiply(3).toString());
	}

	@Test
	void currencyMismatch_throws() {
		Money a = inr("10.00");
		Money usd = new Money(new BigDecimal("1.00"), Currency.getInstance("USD"));
		assertThrows(IllegalArgumentException.class, () -> a.add(usd));
	}

	@Test
	void equalsAndHashCode() {
		Money a1 = inr("10.00");
		Money a2 = inr("10.00");
		assertEquals(a1, a2);
		assertEquals(a1.hashCode(), a2.hashCode());
	}

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

}
