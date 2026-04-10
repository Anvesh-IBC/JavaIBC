package com.ibc.training.shop;

import java.math.BigDecimal;

public interface DiscountStrategy {
	BigDecimal apply(BigDecimal amount);

	String name();
}