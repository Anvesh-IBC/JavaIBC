package com.ibc.training.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PricingUtil {
	private PricingUtil() {
	}

	public static BigDecimal applyGstAndDiscount(BigDecimal fee, double gstRate, BigDecimal discountFraction) {
		BigDecimal discounted = fee.subtract(fee.multiply(discountFraction));
		if (discounted.compareTo(BigDecimal.ZERO) < 0)
			discounted = BigDecimal.ZERO;
		BigDecimal gst = discounted.multiply(BigDecimal.valueOf(gstRate));
		return discounted.add(gst).setScale(2, RoundingMode.HALF_UP);
	}
}
