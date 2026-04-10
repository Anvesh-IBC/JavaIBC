package com.ecommerce.service;

import com.ecommerce.model.Product;

public interface Purchasable {
	void addToCart(Product p, int qty);
}
