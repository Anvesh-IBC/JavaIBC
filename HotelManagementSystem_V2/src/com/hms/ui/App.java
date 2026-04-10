package com.hms.ui;

import com.hms.config.ExecutorsConfig;
import com.hms.dao.*;
import com.hms.event.EventBus;
import com.hms.model.*;
import com.hms.service.*;
import com.hms.util.Dates;
import com.hms.util.PriceCalculator;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class App {

	private final Scanner sc = new Scanner(System.in);

	// ===================== DAOs =====================
	private final RoomDAO roomDAO = new InMemoryRoomDAO();
	private final GuestDAO guestDAO = new InMemoryGuestDAO();
	private final BookingDAO bookingDAO = new InMemoryBookingDAO();
	private final PaymentDAO paymentDAO = new InMemoryPaymentDAO();
	private final InvoiceDAO invoiceDAO = new InMemoryInvoiceDAO();

	// ================= Infrastructure =================
	private final RoomLockRegistry roomLockRegistry = new RoomLockRegistry();
	private final EventBus eventBus = new EventBus();
	private final ExecutorService ioPool = ExecutorsConfig.ioPool();

	// ================= Services =================
	private final RoomService roomService = new RoomService(roomDAO);
	private final GuestService guestService = new GuestService(guestDAO);
	private final BookingService bookingService = new BookingService(bookingDAO, roomDAO, roomLockRegistry, eventBus);

	private final PaymentService paymentService = new PaymentService(paymentDAO);
	private final InvoiceService invoiceService = new InvoiceService(invoiceDAO);

	private final PaymentGateway paymentGateway = new RateLimitedPaymentGateway();
	private final Mailer mailer = new Mailer();

	private final CheckoutService checkoutService = new CheckoutService(bookingService, paymentGateway, paymentService,
			invoiceService, mailer, ioPool);

	public static void main(String[] args) {
		App app = new App();
		app.initPricing();
		app.startEventBus();
		app.seedData();
		app.menu();
	}

	// ================= INIT =================

	private void initPricing() {
		RatesCatalog ratesCatalog = new RatesCatalog();
		PriceCalculator.init(ratesCatalog);
	}

	private void startEventBus() {
		// eventBus.startConsumer(ioPool,
		// event -> System.out.println("Event received: " +
		// event.getClass().getSimpleName()));
	}

	// ================= DATA =================

	private void seedData() {
		roomService.addRoom(new Room(1, RoomType.STANDARD, 1));
		roomService.addRoom(new Room(2, RoomType.DELUXE, 2));
		roomService.addRoom(new Room(3, RoomType.DELUXE, 2));
		roomService.addRoom(new Room(4, RoomType.SUITE, 3));
		roomService.addRoom(new Room(5, RoomType.SUITE, 3));

		guestService.addGuest(new Guest("G-100", "Prasanna", "9876543210", "prasanna@example.com"));
	}

	// ================= MENU =================

	private void menu() {
		while (true) {
			System.out.println("\n=== HOTEL MANAGEMENT SYSTEM (V2) ===");
			System.out.println("1. Add Room");
			System.out.println("2. List Rooms");
			System.out.println("3. Add Guest");
			System.out.println("4. List Guests");
			System.out.println("5. Create Booking + Payment");
			System.out.println("6. Check-In");
			System.out.println("7. Checkout (Release Room)");
			System.out.println("8. List Bookings");
			System.out.println("9. Revenue Report");
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
					createBookingAndPay();
					break;
				case 6:
					checkIn();
					break;
				case 7:
					checkout();
					break;
				case 8:
					listBookings();
					break;
				case 9:
					reportRevenueByMode();
					break;
				case 0:
					ioPool.shutdown();
					System.out.println("Bye!");
					return;
				default:
					System.out.println("Invalid choice");
				}

			} catch (Exception e) {
				System.out.println("Operation failed: " + e.getMessage());
			}
		}
	}

	// ================= HELPERS =================

	private int readInt() {
		while (!sc.hasNextInt())
			sc.next();
		int v = sc.nextInt();
		sc.nextLine();
		return v;
	}

	// ================= ACTIONS =================

	private void addRoom() {
		System.out.print("Room Number: ");
		int roomNo = readInt();

		System.out.print("Room Type (1=STANDARD,2=DELUXE,3=SUITE): ");
		int t = readInt();
		RoomType type = (t == 2) ? RoomType.DELUXE : (t == 3) ? RoomType.SUITE : RoomType.STANDARD;

		System.out.print("Floor: ");
		int floor = readInt();

		Room room = new Room(roomNo, type, floor);
		roomService.addRoom(room);

		System.out.println("Room added: " + room);
	}

//	private void listRooms() {
//		roomService.listAll().forEach(System.out::println);
//	}

	private void listRooms() {

		for (Room room : roomService.listAll()) {

			String bookingStatus = bookingService.findActiveBookingForRoom(room.getRoomNumber())
					.map(b -> b.getStatus().name()).orElse("NONE");

			System.out.println("Room{" + "no=" + room.getRoomNumber() + ", type=" + room.getType() + ", status="
					+ room.getStatus() + ", bookingStatus=" + bookingStatus + ", basePrice=" + room.getBasePrice()
					+ ", floor=" + room.getFloor() + "}");
		}
	}

	private void addGuest() {
		System.out.print("Guest ID: ");
		String id = sc.nextLine().trim();
		System.out.print("Name: ");
		String name = sc.nextLine().trim();
		System.out.print("Phone: ");
		String phone = sc.nextLine().trim();
		System.out.print("Email: ");
		String email = sc.nextLine().trim();

		guestService.addGuest(new Guest(id, name, phone, email));
		System.out.println("Guest added.");
	}

	private void listGuests() {
		guestService.listAll().forEach(System.out::println);
	}

	// MAIN FEATURE
	private void createBookingAndPay() {

		System.out.print("Guest ID: ");
		Guest g = guestService.findById(sc.nextLine().trim())
				.orElseThrow(() -> new RuntimeException("Guest not found"));

		System.out.print("Room Type (1=STANDARD,2=DELUXE,3=SUITE): ");
		int t = readInt();
		RoomType type = (t == 2) ? RoomType.DELUXE : (t == 3) ? RoomType.SUITE : RoomType.STANDARD;

		System.out.print("Check-In (yyyy-MM-dd): ");
		String in = sc.nextLine();

		System.out.print("Check-Out (yyyy-MM-dd): ");
		String out = sc.nextLine();

		Booking b = bookingService.create(g, type, Dates.parse(in), Dates.parse(out));

		System.out.println("Booking ID: " + b.getBookingId());
		System.out.println("Total amount = " + b.totalAmount());

		System.out.print("Choose payment mode: 1 = CARD, 2 = UPI : ");
		int pm = readInt();
		PaymentMode mode = (pm == 2) ? PaymentMode.UPI : PaymentMode.CARD;

		checkoutService.checkout(b.getBookingId(), Currency.INR, b.totalAmount(), mode);

		System.out.println("Booking confirmed. Invoice generated successfully.");
		System.out.println("Booking Details:" + b);

	}

	private void checkIn() {
		System.out.print("Booking ID: ");
		bookingService.checkIn(sc.nextLine().trim());
		System.out.println("Checked in.");
	}

	private void checkout() {
		System.out.print("Booking ID: ");
		bookingService.checkOut(sc.nextLine().trim());
		System.out.println("Room released successfully.");
	}

	private void listBookings() {
		bookingService.listAll().forEach(System.out::println);
	}

	private void reportRevenueByMode() {
		paymentService.revenueByMode().forEach((k, v) -> System.out.println(k + " : " + v));
	}
}
