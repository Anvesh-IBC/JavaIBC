package com.bitebuddy.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class Config {
	private BigDecimal taxPercent = new BigDecimal("5.0");
	private BigDecimal deliveryFee = new BigDecimal("30.00");
	private BigDecimal discountThreshold = new BigDecimal("500.00");
	private BigDecimal discountPercent = new BigDecimal("10.0");

	public void loadFromClasspath(String resourcePath) {
		try (InputStream is = Config.class.getResourceAsStream(resourcePath)) {
			if (is == null)
				return;
			Properties p = new Properties();
			p.load(is);
			if (p.getProperty("tax.percent") != null)
				taxPercent = new BigDecimal(p.getProperty("tax.percent"));
			if (p.getProperty("delivery.fee") != null)
				deliveryFee = new BigDecimal(p.getProperty("delivery.fee"));
			if (p.getProperty("discount.threshold") != null)
				discountThreshold = new BigDecimal(p.getProperty("discount.threshold"));
			if (p.getProperty("discount.percent") != null)
				discountPercent = new BigDecimal(p.getProperty("discount.percent"));
		} catch (Exception e) {
			System.err.println("Config load error: " + e.getMessage());
		}
	}

	public BigDecimal getTaxPercent() {
		return taxPercent;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public BigDecimal getDiscountThreshold() {
		return discountThreshold;
	}

	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}
}