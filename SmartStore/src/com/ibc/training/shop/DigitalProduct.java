package com.ibc.training.shop;

import java.math.BigDecimal;

public class DigitalProduct extends Product {
	private final String downloadUrl;

	public DigitalProduct(int id, String name, BigDecimal price, int stock, String downloadUrl) {
		super(id, name, price, stock);
		this.downloadUrl = downloadUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}
}