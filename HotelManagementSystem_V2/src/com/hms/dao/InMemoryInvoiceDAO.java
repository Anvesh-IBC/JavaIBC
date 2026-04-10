package com.hms.dao;

import com.hms.model.Invoice;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryInvoiceDAO implements InvoiceDAO {
	private final Map<String, Invoice> byBooking = new ConcurrentHashMap<>();

	@Override
	public void save(Invoice invoice) {
		byBooking.put(invoice.getBookingId(), invoice);
	}

	@Override
	public Optional<Invoice> findByBooking(String bookingId) {
		return Optional.ofNullable(byBooking.get(bookingId));
	}
}