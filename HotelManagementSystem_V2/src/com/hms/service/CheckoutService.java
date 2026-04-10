package com.hms.service;

import com.hms.model.*;
import com.hms.util.Futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class CheckoutService {

	private final BookingService bookingService;
	private final PaymentGateway paymentGateway;
	private final PaymentService paymentService;
	private final InvoiceService invoiceService;
	private final Mailer mailer;
	private final ExecutorService ioPool;

	public CheckoutService(BookingService bookingService, PaymentGateway paymentGateway, PaymentService paymentService,
			InvoiceService invoiceService, Mailer mailer, ExecutorService ioPool) {
		this.bookingService = bookingService;
		this.paymentGateway = paymentGateway;
		this.paymentService = paymentService;
		this.invoiceService = invoiceService;
		this.mailer = mailer;
		this.ioPool = ioPool;
	}

	// UPDATED SIGNATURE
	public CompletableFuture<Void> checkout(String bookingId, Currency currency, double totalPaid,
			PaymentMode paymentMode) {

		// 1 Load booking (ASYNC)
		CompletableFuture<Booking> bookingFuture = supplyAsync(() -> bookingService.load(bookingId));

		// 2 Payment gateway + internal payment record
		CompletableFuture<Booking> paymentFuture = bookingFuture.thenCompose(booking -> {

			CompletableFuture<String> gatewayCall = supplyAsync(
					() -> paymentGateway.charge(booking.getGuest().getEmail(), totalPaid), ioPool);

			return Futures.withTimeout(gatewayCall, 2, TimeUnit.SECONDS).thenApply(paymentId -> {
				paymentService.pay(booking.getBookingId(), paymentMode, totalPaid);
				return booking;
			});
		});

		// 3 Invoice generation + export
		CompletableFuture<Object[]> invoiceFuture = paymentFuture.thenCompose(booking -> {

			CompletableFuture<Object[]> invoiceTask = supplyAsync(() -> {

				Invoice inv = invoiceService.generate(booking, currency, totalPaid);

				String path = System.getProperty("user.dir") + java.io.File.separator + "invoice_" + inv.getInvoiceNo()
						+ ".txt";

				//System.out.println("Invoice generated successfully.");
				//System.out.println("Invoice saved at: " + path);


				invoiceService.exportToFile(inv, booking, path);

				return new Object[] { booking, inv, path };

			}, ioPool);

			return Futures.withTimeout(invoiceTask, 2, TimeUnit.SECONDS);
		});

		// 4 SUCCESS / FAILURE handling
		return invoiceFuture.exceptionally(ex -> {
			handleCheckoutFailure(bookingId, ex);
			return null;
		}).thenAccept(result -> {

			if (result == null)
				return;

			Booking booking = (Booking) result[0];
			Invoice inv = (Invoice) result[1];
			String path = (String) result[2];

			// CONFIRM BOOKING
			bookingService.confirm(booking.getBookingId());
			System.out.println("Booking " + booking.getBookingId() + " CONFIRMED");

			// SEND EMAIL
			mailer.send(booking.getGuest().getEmail(), "Invoice " + inv.getInvoiceNo(),
					"Thank you for staying with us!", path);
		});
	}

	// ---------------- FAILURE HANDLING ----------------

	private void handleCheckoutFailure(String bookingId, Throwable ex) {

		System.err.println("Checkout failed for booking " + bookingId + " : " + ex.getMessage());

		try {
			bookingService.cancel(bookingId);
		} catch (Exception ignored) {
		}
	}
}
