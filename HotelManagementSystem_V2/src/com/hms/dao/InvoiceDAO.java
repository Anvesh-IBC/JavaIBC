package com.hms.dao;

import com.hms.model.Invoice;
import java.util.Optional;

public interface InvoiceDAO {
	void save(Invoice invoice);

	Optional<Invoice> findByBooking(String bookingId);
}
