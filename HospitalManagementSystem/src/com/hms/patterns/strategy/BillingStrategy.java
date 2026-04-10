package com.hms.patterns.strategy;

import com.hms.domain.Money;

public interface BillingStrategy {
	Money compute(Money base, int days);
}
