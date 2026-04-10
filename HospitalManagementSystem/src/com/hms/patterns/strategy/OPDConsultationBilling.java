package com.hms.patterns.strategy;

import com.hms.domain.Money;

public class OPDConsultationBilling implements BillingStrategy {
	@Override
	public Money compute(Money base, int days) {
		return base;
	} // flat fee
}
