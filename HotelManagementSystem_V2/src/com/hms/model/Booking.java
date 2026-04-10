package com.hms.model;

import java.time.LocalDate;

public class Booking {
	private String bookingId;
	private Room room;
	private Guest guest;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private BookingStatus status = BookingStatus.CREATED;
	private double nightlyRate;
	private double tax;
	private double discount;

	public Booking() {
	}

	public Booking(String bookingId, Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
		this.bookingId = bookingId;
		this.guest = guest;
		this.room = room;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public double getNightlyRate() {
		return nightlyRate;
	}

	public void setNightlyRate(double nightlyRate) {
		this.nightlyRate = nightlyRate;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getNights() {
		return (int) (checkOut.toEpochDay() - checkIn.toEpochDay());
	}

	public double totalAmount() {
		return nightlyRate * getNights() + tax - discount;
	}

	@Override
	public String toString() {
		return "Booking{" + "bookingId='" + bookingId + '\'' + ", room=" + (room != null ? room.getRoomNumber() : null)
				+ ", guest=" + (guest != null ? guest.getGuestId() : null) + ", checkIn=" + checkIn + ", checkOut="
				+ checkOut + ", status=" + status + ", nightlyRate=" + nightlyRate + ", tax=" + tax + ", discount="
				+ discount + ", total=" + totalAmount() + '}';
	}
}
