package com.bitebuddy.service.impl;

import com.bitebuddy.domain.CartItem;
import com.bitebuddy.domain.MenuItem;
import com.bitebuddy.service.CartService;
import com.bitebuddy.util.Validators;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class SimpleCartService implements CartService {
	public void addItem(List<CartItem> cart, MenuItem item, int qty) {
		Validators.requireQty(qty);
		for (CartItem ci : cart) {
			if (ci.getItem().getId() == item.getId()) {
				ci.setQuantity(ci.getQuantity() + qty);
				return;
			}
		}
		cart.add(new CartItem(item, qty));
	}

	public void removeItem(List<CartItem> cart, int itemId) {
		Iterator<CartItem> it = cart.iterator();
		while (it.hasNext()) {
			CartItem ci = it.next();
			if (ci.getItem().getId() == itemId) {
				it.remove();
				return;
			}
		}
	}

	public BigDecimal computeSubtotal(List<CartItem> cart) {
		BigDecimal subtotal = BigDecimal.ZERO;
		for (CartItem ci : cart) {
			BigDecimal line = ci.getItem().getPrice().multiply(new BigDecimal(ci.getQuantity()));
			subtotal = subtotal.add(line);
		}
		return subtotal;
	}
}
