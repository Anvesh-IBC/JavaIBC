package com.hms.service;

import com.hms.dao.InvoiceDAO;
import com.hms.model.*;
import com.hms.util.Ids;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.text.NumberFormat;
import java.util.Locale;

public class InvoiceService {
	private final InvoiceDAO invoiceDAO;

	public InvoiceService(InvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	public Invoice generate(Booking booking, Currency currency, double totalPaid) {
		double total = booking.totalAmount();
		if (totalPaid + 0.001 < total)
			throw new IllegalStateException("Payment incomplete. Total: " + total + ", Paid: " + totalPaid);
		String invNo = Ids.invoiceNo();
		Invoice inv = new Invoice(invNo, booking.getBookingId(), total, currency, LocalDateTime.now());
		invoiceDAO.save(inv);
		return inv;
	}

	public void exportToFile(Invoice inv, Booking booking, String filePath) {
		NumberFormat nf = NumberFormat
				.getCurrencyInstance(new Locale("en", inv.getCurrency() == Currency.INR ? "IN" : "US"));
		StringBuilder sb = new StringBuilder(256).append("===== INVOICE =====\n").append("Invoice No : ")
				.append(inv.getInvoiceNo()).append('\n').append("Booking Id : ").append(inv.getBookingId()).append('\n')
				.append("Guest      : ").append(booking.getGuest().getName()).append('\n').append("Room       : ")
				.append(booking.getRoom().getRoomNumber()).append(" (").append(booking.getRoom().getType())
				.append(")\n").append("Check-In   : ").append(booking.getCheckIn()).append('\n').append("Check-Out  : ")
				.append(booking.getCheckOut()).append('\n').append("Nights     : ").append(booking.getNights())
				.append('\n').append("Nightly    : ").append(nf.format(booking.getNightlyRate())).append('\n')
				.append("Tax        : ").append(nf.format(booking.getTax())).append('\n').append("Discount   : ")
				.append(nf.format(booking.getDiscount())).append('\n').append("TOTAL      : ")
				.append(nf.format(inv.getTotal())).append('\n').append("Generated  : ").append(inv.getCreatedAt())
				.append('\n');

		try (FileWriter fw = new FileWriter(filePath)) {
			fw.write(sb.toString());
		} catch (IOException e) {
			throw new RuntimeException("Failed to write invoice: " + e.getMessage(), e);
		}
	}
}
