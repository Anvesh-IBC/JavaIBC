package com.bitebuddy.service.impl;

import com.bitebuddy.domain.*;
import com.bitebuddy.repository.InMemoryOrderRepository;
import com.bitebuddy.service.OrderService;
import com.bitebuddy.util.Config;
import com.bitebuddy.util.IdGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleOrderService implements OrderService {
	private final InMemoryOrderRepository orderRepo;
	private final Config config;

	public SimpleOrderService(InMemoryOrderRepository orderRepo, Config config) {
		this.orderRepo = orderRepo;
		this.config = config;
	}

	public Order placeOrder(Customer customer, List<CartItem> cart, PaymentMethod method) {
        if (cart == null || cart.isEmpty()) throw new IllegalArgumentException("Cart is empty");
        long id = IdGenerator.nextOrderId();
        Order o = new Order(id, customer, cart);

        // Pricing
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem ci : cart) {
            BigDecimal line = ci.getItem().getPrice().multiply(new BigDecimal(ci.getQuantity()));
            subtotal = subtotal.add(line);
        }
        BigDecimal tax = subtotal.multiply(config.getTaxPercent().movePointLeft(2));
        BigDecimal discount = subtotal.compareTo(config.getDiscountThreshold()) >= 0
                ? subtotal.multiply(config.getDiscountPercent().movePointLeft(2))
                : BigDecimal.ZERO;
        BigDecimal delivery = config.getDeliveryFee();
        BigDecimal total = subtotal.add(tax).add(delivery).subtract(discount);

        o.setSubtotal(subtotal.setScale(2, BigDecimal.ROUND_HALF_UP));
        o.setTax(tax.setScale(2, BigDecimal.ROUND_HALF_UP));
        o.setDiscount(discount.setScale(2, BigDecimal.ROUND_HALF_UP));
        o.setDeliveryFee(delivery.setScale(2, BigDecimal.ROUND_HALF_UP));
        o.setTotal(total.setScale(2, BigDecimal.ROUND_HALF_UP));
        o.setStatus(OrderStatus.CREATED);

        orderRepo.save(o);
        return o;
    }

	public void updateStatus(long orderId, OrderStatus status) {
		Order o = orderRepo.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found: " + orderId));
		o.setStatus(status);
		orderRepo.save(o);
	}

	public List<Order> getOrdersBetween(LocalDateTime start, LocalDateTime end) {
		return orderRepo.findAll().stream()
				.filter(o -> !o.getCreatedAt().isBefore(start) && !o.getCreatedAt().isAfter(end))
				.sorted((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt())).collect(Collectors.toList());
	}
}
