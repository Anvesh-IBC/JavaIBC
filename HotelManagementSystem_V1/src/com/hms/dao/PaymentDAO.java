package com.hms.dao;

import com.hms.model.PaymentRecord;
import java.util.List;

public interface PaymentDAO {
	void save(PaymentRecord payment);

	List<PaymentRecord> findByBooking(String bookingId);

	List<PaymentRecord> findAll();
}
