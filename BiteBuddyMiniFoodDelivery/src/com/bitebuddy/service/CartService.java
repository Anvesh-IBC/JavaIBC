package com.bitebuddy.service;

import com.bitebuddy.domain.CartItem;
import com.bitebuddy.domain.MenuItem;
import java.math.BigDecimal;
import java.util.List;

public interface CartService {
	void addItem(List<CartItem> cart, MenuItem item, int qty);

	void removeItem(List<CartItem> cart, int itemId);

	BigDecimal computeSubtotal(List<CartItem> cart);
}
