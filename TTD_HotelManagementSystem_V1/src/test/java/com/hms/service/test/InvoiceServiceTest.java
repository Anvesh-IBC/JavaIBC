package com.hms.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.util.Currency;
import com.hms.model.Currency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hms.dao.InvoiceDAO;
import com.hms.model.Booking;
import com.hms.model.Guest;
import com.hms.model.Invoice;
import com.hms.model.Room;
import com.hms.model.RoomType;
import com.hms.service.InvoiceService;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

	@Mock
	private InvoiceDAO invoiceDAO;
	@InjectMocks
	private InvoiceService service;

	private Booking booking() {
		Guest g = new Guest("G-1", "Prasanna", "9876543210", "p@e.com");
		Room r = new Room(101, RoomType.STANDARD, 1);
		Booking b = new Booking("BK-1", g, r, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 4)); // 3 nights
		b.setNightlyRate(3000); // subtotal 9000
		b.setTax(1080); // 12%
		b.setDiscount(0);
		return b;
	}

	@Test
	void generate_paidEnough_createsAndSavesInvoice() {
		Booking b = booking();
		double total = b.totalAmount(); // 3000*3 + 1080 = 10080
		Invoice inv = service.generate(b, Currency.INR, total);
		assertNotNull(inv.getInvoiceNo());
		assertEquals("BK-1", inv.getBookingId());
		assertEquals(total, inv.getTotal(), 0.0001);
		assertEquals(Currency.INR, inv.getCurrency());
		verify(invoiceDAO).save(inv);
	}

	@Test
	void generate_insufficientPayment_throws() {
		Booking b = booking();
		assertThrows(IllegalStateException.class, () -> service.generate(b, Currency.USD, 10));
		verifyNoInteractions(invoiceDAO);
	}

	@Test
	void exportToFile_writesAllKeyFields() throws IOException {
		Booking b = booking();
		Invoice inv = new Invoice("INV-1", "BK-1", b.totalAmount(), Currency.INR, LocalDateTime.now());
		Path tmp = Files.createTempFile("inv", ".txt");
		try {
			service.exportToFile(inv, b, tmp.toString());
			String content = new String(Files.readAllBytes(tmp), "UTF-8");

			assertThat(content).contains("===== INVOICE =====").contains("Invoice No : INV-1")
					.contains("Booking Id : BK-1").contains("Guest      : Prasanna")
					.contains("Room       : 101 (STANDARD)").contains("TOTAL      :");
		} finally {
			Files.deleteIfExists(tmp);
		}
	}
}