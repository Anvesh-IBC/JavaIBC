package com.bitebuddy.service;

import com.bitebuddy.domain.*;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
	Order placeOrder(Customer customer, List<CartItem> cart, PaymentMethod method);

	void updateStatus(long orderId, OrderStatus status);

	List<Order> getOrdersBetween(LocalDateTime start, LocalDateTime end);
}
