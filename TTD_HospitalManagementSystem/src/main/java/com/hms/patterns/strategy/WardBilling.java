package com.hms.patterns.strategy;

import com.hms.domain.Money;

public class WardBilling implements BillingStrategy {
	@Override
	public Money compute(Money perDay, int days) {
		return perDay.multiply(days);
	}	
}
