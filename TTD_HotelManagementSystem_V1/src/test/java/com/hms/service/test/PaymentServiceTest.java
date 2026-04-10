package com.hms.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hms.dao.PaymentDAO;
import com.hms.model.PaymentMode;
import com.hms.model.PaymentRecord;
import com.hms.service.PaymentService;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

	@Mock
	private PaymentDAO paymentDAO;
	@InjectMocks
	private PaymentService service;

	@Test
	void pay_invalidAmount_throws() {
		assertThrows(IllegalArgumentException.class, () -> service.pay("BK-1", PaymentMode.CASH, 0));
		verifyNoInteractions(paymentDAO);
	}

	@Test
	void pay_valid_savesRecord() {
		ArgumentCaptor<PaymentRecord> cap = ArgumentCaptor.forClass(PaymentRecord.class);
		PaymentRecord pr = service.pay("BK-1", PaymentMode.UPI, 1200.0);
		verify(paymentDAO).save(cap.capture());
		PaymentRecord saved = cap.getValue();
		assertEquals("BK-1", saved.getBookingId());
		assertEquals(PaymentMode.UPI, saved.getMode());
		assertEquals(1200.0, saved.getAmount(), 0.0001);

		assertEquals(pr.getPaymentId(), saved.getPaymentId()); // returned same object
	}

	@Test
	void totalPaid_sumsFromDAO() {
		when(paymentDAO.findByBooking("BK-1"))
				.thenReturn(Arrays.asList(new PaymentRecord("P1", "BK-1", PaymentMode.CASH, 1000, null),
						new PaymentRecord("P2", "BK-1", PaymentMode.CARD, 500, null)));
		assertEquals(1500.0, service.totalPaid("BK-1"), 0.0001);
	}

	@Test
	void revenueByMode_groupsAndSums() {
		when(paymentDAO.findAll())
				.thenReturn(Arrays.asList(new PaymentRecord("P1", "BK-1", PaymentMode.CASH, 100, null),
						new PaymentRecord("P2", "BK-1", PaymentMode.CARD, 200, null),
						new PaymentRecord("P3", "BK-2", PaymentMode.CASH, 300, null)));
		Map<PaymentMode, Double> map = service.revenueByMode();
		assertThat(map).containsEntry(PaymentMode.CASH, 400.0).containsEntry(PaymentMode.CARD, 200.0)
				.doesNotContainKey(PaymentMode.UPI);
	}
}
