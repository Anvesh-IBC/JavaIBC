package com.bitebuddy.facade;

import com.bitebuddy.domain.*;
import com.bitebuddy.exceptions.PaymentFailedException;
import com.bitebuddy.service.OrderService;
import com.bitebuddy.service.PaymentService;
import com.bitebuddy.service.RiderAssignmentService;
import com.bitebuddy.service.impl.CardPaymentService;
import com.bitebuddy.service.impl.CashPaymentService;
import com.bitebuddy.service.impl.UpiPaymentService;

public class OrderFacade {
	private final OrderService orderService;
	private final RiderAssignmentService riderService;
	private final PaymentService upi = new UpiPaymentService();
	private final PaymentService card = new CardPaymentService();
	private final PaymentService cash = new CashPaymentService();

	public OrderFacade(OrderService orderService, RiderAssignmentService riderService) {
		this.orderService = orderService;
		this.riderService = riderService;
	}

	public Rider assignRider() {
		return riderService.assignRider();
	}

	public Order checkout(Customer c, java.util.List<CartItem> cart, PaymentMethod method)
			throws PaymentFailedException {
		Order o = orderService.placeOrder(c, cart, method);
		PaymentService p = method == PaymentMethod.UPI ? upi : method == PaymentMethod.CARD ? card : cash;
		p.process(o);
		return o;
	}
}
