package com.hms.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Test;

import com.hms.domain.Money;
import com.hms.patterns.strategy.OPDConsultationBilling;
import com.hms.patterns.strategy.WardBilling;
import com.hms.service.BillingService;

class BillingServiceTest {

	@Test
	void opd_isFlatFee() {
		BillingService bs = new BillingService(new OPDConsultationBilling());
		Money fee = new Money(new BigDecimal("500.00"), Currency.getInstance("INR"));
		assertEquals("INR 500.00", bs.compute(fee, 3).toString());
	}

	@Test
	void ward_isPerDay() {
		BillingService bs = new BillingService(new WardBilling());
		Money perDay = new Money(new BigDecimal("2000.00"), Currency.getInstance("INR"));
		assertEquals("INR 6000.00", bs.compute(perDay, 3).toString());
		bs.setStrategy(new OPDConsultationBilling());
		assertEquals("INR 2000.00", bs.compute(perDay, 3).toString()); // flat fee now
	}
}
