package com.hms.dao;

import com.hms.model.PaymentRecord;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryPaymentDAO implements PaymentDAO {

    private final List<PaymentRecord> store = new CopyOnWriteArrayList<>();

    @Override
    public void save(PaymentRecord payment) {
        store.add(payment);
    }

    @Override
    public List<PaymentRecord> findByBooking(String bookingId) {
        List<PaymentRecord> res = new CopyOnWriteArrayList<>();
        for (PaymentRecord pr : store) {
            if (bookingId.equals(pr.getBookingId())) {
                res.add(pr);
            }
        }
        return res;
    }

    @Override
    public List<PaymentRecord> findAll() {
        return new CopyOnWriteArrayList<>(store);
    }
}
