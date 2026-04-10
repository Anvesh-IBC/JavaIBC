package com.hms.service;

import com.hms.domain.Money;
import com.hms.patterns.strategy.BillingStrategy;

public class BillingService {
	private BillingStrategy strategy;

	public BillingService(BillingStrategy s) {
		this.strategy = s;
	}

	public void setStrategy(BillingStrategy s) {
		this.strategy = s;
	}

	public Money compute(Money base, int days) {
		return strategy.compute(base, days);
	}
}
