package com.hms.ui;

import com.hms.dao.*;
import com.hms.model.*;
import com.hms.model.Currency;
import com.hms.service.*;
import com.hms.util.Dates;

import java.util.*;
import java.util.stream.Collectors;

public class App {

	private final Scanner sc = new Scanner(System.in);

	// DAOs
	private final RoomDAO roomDAO = new InMemoryRoomDAO();
	private final GuestDAO guestDAO = new InMemoryGuestDAO();
	private final BookingDAO bookingDAO = new InMemoryBookingDAO();
	private final PaymentDAO paymentDAO = new InMemoryPaymentDAO();
	private final InvoiceDAO invoiceDAO = new InMemoryInvoiceDAO();

	// Services
	private final RoomService roomService = new RoomService(roomDAO);
	private final GuestService guestService = new GuestService(guestDAO);
	private final BookingService bookingService = new BookingService(bookingDAO, roomDAO);
	private final PaymentService paymentService = new PaymentService(paymentDAO);
	private final InvoiceService invoiceService = new InvoiceService(invoiceDAO);

	public static void main(String[] args) {
		App app = new App();
		app.seedData();
		app.menu();
	}

	private void seedData() {
		// Seed some rooms
		for (int i = 101; i <= 110; i++)
			roomService.addRoom(new Room(i, RoomType.STANDARD, 1));
		for (int i = 201; i <= 206; i++)
			roomService.addRoom(new Room(i, RoomType.DELUXE, 2));
		for (int i = 301; i <= 303; i++)
			roomService.addRoom(new Room(i, RoomType.SUITE, 3));

		// Seed one guest
		guestService.addGuest(new Guest("G-100", "Prasanna", "9876543210", "prasanna@example.com"));
	}

	private void menu() {
		while (true) {
			System.out.println("\n=== HOTEL MANAGEMENT SYSTEM ===");
			System.out.println("1. Add Room");
			System.out.println("2. List Rooms");
			System.out.println("3. Add Guest");
			System.out.println("4. List Guests");
			System.out.println("5. Create Booking");
			System.out.println("6. Confirm Booking");
			System.out.println("7. Check-In");
			System.out.println("8. Record Payment");
			System.out.println("9. Check-Out & Generate Invoice");
			System.out.println("10. List Bookings");
			System.out.println("11. Reports: Revenue by Payment Mode");
			System.out.println("0. Exit");
			System.out.print("Enter choice: ");

			int ch = readInt();
			try {
				switch (ch) {
				case 1:
					addRoom();
					break;
				case 2:
					listRooms();
					break;
				case 3:
					addGuest();
					break;
				case 4:
					listGuests();
					break;
				case 5:
					createBooking();
					break;
				case 6:
					confirmBooking();
					break;
				case 7:
					checkIn();
					break;
				case 8:
					recordPayment();
					break;
				case 9:
					checkoutAndInvoice();
					break;
				case 10:
					listBookings();
					break;
				case 11:
					reportRevenueByMode();
					break;
				case 0:
					System.out.println("Bye!");
					return;
				default:
					System.out.println("Invalid choice.");
				}
			} catch (Exception e) {
				System.out.println("Operation failed: " + e.getMessage());
			}
		}
	}

	private int readInt() {
		while (!sc.hasNextInt()) {
			System.out.print("Enter a number: ");
			sc.next();
		}
		int v = sc.nextInt();
		sc.nextLine();
		return v;
	}

	private double readDouble() {
		while (!sc.hasNextDouble()) {
			System.out.print("Enter a decimal number: ");
			sc.next();
		}
		double v = sc.nextDouble();
		sc.nextLine();
		return v;
	}

	private void addRoom() {
		System.out.print("Room Number: ");
		int no = readInt();
		System.out.print("Type (1=STANDARD, 2=DELUXE, 3=SUITE): ");
		int t = readInt();
		RoomType type = (t == 2) ? RoomType.DELUXE : (t == 3 ? RoomType.SUITE : RoomType.STANDARD);
		System.out.print("Floor: ");
		int floor = readInt();
		Room r = new Room(no, type, floor);
		System.out.print("Base Price (enter 0 to keep default): ");
		double bp = readDouble();
		if (bp > 0)
			r.setBasePrice(bp);
		roomService.addRoom(r);
		System.out.println("Added: " + r);
	}

	private void listRooms() {
		List<Room> rooms = roomService.listAll().stream().sorted(Comparator.comparingInt(Room::getRoomNumber))
				.collect(Collectors.toList());
		rooms.forEach(System.out::println);
		System.out.println("Total rooms: " + rooms.size());
	}

	private void addGuest() {
		System.out.print("Guest ID (e.g., G-101): ");
		String id = sc.nextLine().trim();
		System.out.print("Name: ");
		String name = sc.nextLine().trim();
		System.out.print("Phone (10 digits): ");
		String phone = sc.nextLine().trim();
		System.out.print("Email: ");
		String email = sc.nextLine().trim();
		guestService.addGuest(new Guest(id, name, phone, email));
		System.out.println("Guest added.");
	}

	private void listGuests() {
		guestService.listAll().forEach(System.out::println);
	}

	private void createBooking() {
		System.out.print("Guest ID: ");
		String gid = sc.nextLine().trim();
		Guest g = guestService.findById(gid).orElseThrow(() -> new RuntimeException("Guest not found"));

		System.out.print("Room Type (1=STANDARD, 2=DELUXE, 3=SUITE): ");
		int t = readInt();
		RoomType type = (t == 2) ? RoomType.DELUXE : (t == 3 ? RoomType.SUITE : RoomType.STANDARD);

		System.out.print("Check-In (yyyy-MM-dd): ");
		String in = sc.nextLine();
		System.out.print("Check-Out (yyyy-MM-dd): ");
		String out = sc.nextLine();

		Booking b = bookingService.create(g, type, Dates.parse(in), Dates.parse(out));
		System.out.println("Booking created: " + b);
	}

	private void confirmBooking() {
		System.out.print("Booking ID: ");
		String bid = sc.nextLine().trim();
		bookingService.confirm(bid);
		System.out.println("Confirmed.");
	}

	private void checkIn() {
		System.out.print("Booking ID: ");
		String bid = sc.nextLine().trim();
		bookingService.checkIn(bid);
		System.out.println("Checked in.");
	}

	private void recordPayment() {
		System.out.print("Booking ID: ");
		String bid = sc.nextLine().trim();
		System.out.print("Amount: ");
		double amt = readDouble();
		System.out.print("Mode (1=CASH, 2=CARD, 3=UPI): ");
		int m = readInt();
		PaymentMode mode = (m == 2) ? PaymentMode.CARD : (m == 3 ? PaymentMode.UPI : PaymentMode.CASH);
		PaymentRecord pr = paymentService.pay(bid, mode, amt);
		System.out.println("Payment recorded: " + pr);
		double paid = paymentService.totalPaid(bid);
		System.out.println("Total paid for " + bid + " = " + paid);
	}

	private void checkoutAndInvoice() {
		System.out.print("Booking ID: ");
		String bid = sc.nextLine().trim();
		Booking b = bookingService.load(bid);
		double paid = paymentService.totalPaid(bid);
		System.out.println("Total due: " + b.totalAmount() + ", Paid: " + paid);

		bookingService.checkOut(bid);

		Invoice inv = invoiceService.generate(b, Currency.INR, paid);
		String path = "invoice_" + inv.getInvoiceNo() + ".txt";
		invoiceService.exportToFile(inv, b, path);
		System.out.println("Checked out. Invoice generated: " + inv + " => " + path);
	}

	private void listBookings() {
		bookingService.listAll().forEach(System.out::println);
	}

	private void reportRevenueByMode() {
		Map<PaymentMode, Double> report = paymentService.revenueByMode();
		if (report.isEmpty()) {
			System.out.println("No payments yet.");
			return;
		}
		report.forEach((k, v) -> System.out.println(k + " : " + v));
	}
}
