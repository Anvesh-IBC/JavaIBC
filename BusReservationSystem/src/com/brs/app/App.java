package com.brs.app;

import com.brs.enums.PaymentMethod;
import com.brs.enums.ServiceType;
import com.brs.model.Bus;
import com.brs.model.Passenger;
import com.brs.model.Route;
import com.brs.model.Seat;
import com.brs.model.Trip;
import com.brs.repo.BookingRepository;
import com.brs.repo.BusRepository;
import com.brs.repo.PaymentRepository;
import com.brs.repo.RouteRepository;
import com.brs.repo.TripRepository;
import com.brs.service.CatalogService;
import com.brs.service.EmailNotificationService;
import com.brs.service.FareService;
import com.brs.service.NotificationService;
import com.brs.service.ReservationService;
import com.brs.demo.PassByValueDemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicLong;

public class App {

	// Repositories
	private final RouteRepository routeRepo = new RouteRepository();
	private final BusRepository busRepo = new BusRepository();
	private final TripRepository tripRepo = new TripRepository();
	private final BookingRepository bookingRepo = new BookingRepository();
	private final PaymentRepository paymentRepo = new PaymentRepository();

	// Services
	private final FareService fareService = new FareService();
	private final NotificationService notifier = new EmailNotificationService();
	private final ReservationService reservationService = new ReservationService(fareService, tripRepo, bookingRepo,
			paymentRepo, notifier);
	private final CatalogService catalogService = new CatalogService(tripRepo);

	private final AtomicLong busSeq = new AtomicLong(200);
	private final AtomicLong tripSeq = new AtomicLong(300);

	public static void main(String[] args) {
		new App().run();
	}

	private void run() {
		seedData();
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			printMenu();
			System.out.print("Enter command: ");
			String input = scanner.nextLine().trim();

			// String APIs: trim, split, toUpperCase
			String[] tokens = input.split("\\s+");
			String cmd = tokens[0].toUpperCase();

			try {
				if ("ADD_ROUTE".equals(cmd)) {
					handleAddRoute(scanner);
				} else if ("ADD_TRIP".equals(cmd)) {
					handleAddTrip(scanner);
				} else if ("LIST_TRIPS".equals(cmd)) {
					listTrips();
				} else if ("SEARCH_TRIPS".equals(cmd)) {
					searchTrips(scanner);
				} else if ("BOOK".equals(cmd)) {
					handleBook(scanner);
				} else if ("CANCEL".equals(cmd)) {
					handleCancel(scanner);
				} else if ("DEMO_PBV".equals(cmd)) {
					demoPassByValue();
				} else if ("TREE_NAV".equals(cmd)) {
					demoTreeNavigation();
				} else if ("BUS_CAPS".equals(cmd)) {
					SortedSet<Integer> caps = busRepo.sortedCapacities();
					System.out.println("Sorted capacities: " + caps); // TreeSet
				} else if ("EXIT".equals(cmd)) {
					exit = true;
				} else {
					System.out.println("Unknown command. Try again.");
				}
			} catch (Exception ex) {
				System.out.println("Error: " + ex.getMessage());
			}
		}

		System.out.println("Goodbye!");
	}

	private void printMenu() {
		System.out.println("\n--- MENU ---");
		System.out.println("ADD_ROUTE     | ADD_TRIP");
		System.out.println("LIST_TRIPS    | SEARCH_TRIPS");
		System.out.println("BOOK          | CANCEL");
		System.out.println("DEMO_PBV      | TREE_NAV | BUS_CAPS");
		System.out.println("EXIT");
	}

	private void seedData() {
		// Add routes
		Route blrMys = new Route("BLR-MYS", "Bengaluru", "Mysuru", 150.0, 2.50, ServiceType.NON_AC);
		routeRepo.upsert(blrMys);

		Route blrHyd = new Route("BLR-HYD", "Bengaluru", "Hyderabad", 570.0, 2.20, ServiceType.AC);
		routeRepo.upsert(blrHyd);

		// Add buses
		Bus bus1 = new Bus(busSeq.incrementAndGet(), "KA-01-AA-1234", ServiceType.AC, Integer.valueOf(44));
		Bus bus2 = new Bus(busSeq.incrementAndGet(), "KA-01-BB-5678", ServiceType.NON_AC, null); // default 40 via init
																									// block
		busRepo.add(bus1);
		busRepo.add(bus2);

		// Trips for today & tomorrow (Java 7: Date + "HH:mm" strings)
		Date today = dateOnly(new Date());
		Date tomorrow = addDays(today, 1);

		Trip t1 = new Trip(tripSeq.incrementAndGet(), blrMys, bus2, today, "09:00", "12:00");
		Trip t2 = new Trip(tripSeq.incrementAndGet(), blrHyd, bus1, tomorrow, "20:00", "07:30");

		tripRepo.add(t1);
		tripRepo.add(t2);

		System.out.println("Seeded Routes, Buses, and Trips.");
	}

	private void handleAddRoute(Scanner sc) {
		System.out.println("Enter: code origin destination distanceKm baseFarePerKm serviceType(AC/NON_AC)");
		String line = sc.nextLine().trim();
		String[] parts = line.split("\\s+");
		if (parts.length < 6)
			throw new IllegalArgumentException("Insufficient fields.");

		String code = parts[0];
		String origin = parts[1];
		String destination = parts[2];
		double distanceKm = Double.parseDouble(parts[3]);
		double baseFarePerKm = Double.parseDouble(parts[4]);
		ServiceType serviceType = parts[5].equalsIgnoreCase("AC") ? ServiceType.AC : ServiceType.NON_AC;

		routeRepo.upsert(new Route(code, origin, destination, distanceKm, baseFarePerKm, serviceType));
		System.out.println("Route added/updated: " + code);
	}

	private void handleAddTrip(Scanner sc) throws ParseException {
		System.out.println("Enter: routeCode busId date(YYYY-MM-DD) dep(HH:mm) arr(HH:mm)");
		String line = sc.nextLine().trim();
		String[] parts = line.split("\\s+");
		if (parts.length < 5)
			throw new IllegalArgumentException("Insufficient fields.");

		String routeCode = parts[0];
		long busId = Long.parseLong(parts[1]);
		Date date = parseDate(parts[2]);
		String dep = parseTime(parts[3]);
		String arr = parseTime(parts[4]);

		Route route = routeRepo.findByCode(routeCode);
		if (route == null)
			throw new IllegalArgumentException("Route not found.");

//		Bus bus = busRepo.find(busId);
//		if (bus == null)
//			throw new IllegalArgumentException("Bus not found.");

		Bus bus = busRepo.find(busId).orElseThrow(() -> new IllegalArgumentException("Bus not found."));

		Trip trip = new Trip(tripSeq.incrementAndGet(), route, bus, date, dep, arr);
		tripRepo.add(trip);
		System.out.println("Trip added: " + trip);
	}

	private void listTrips() {
		List<Trip> sorted = catalogService.listAllSortedByDate();
		System.out.println("Trips:");
		for (int i = 0; i < sorted.size(); i++) {
			System.out.println("  " + sorted.get(i));
		}
	}

	private void searchTrips(Scanner sc) throws ParseException {
		System.out.println("Enter: fromDate(YYYY-MM-DD) toDate(YYYY-MM-DD)");
		String line = sc.nextLine().trim();
		String[] parts = line.split("\\s+");
		Date from = parseDate(parts[0]);
		Date to = parseDate(parts[1]);

		List<Trip> result = catalogService.searchByDateRange(from, to);
		if (result.isEmpty()) {
			System.out.println("No trips found.");
		} else {
			for (int i = 0; i < result.size(); i++) {
				System.out.println("  " + result.get(i));
			}
		}
	}

	private void handleBook(Scanner sc) {
		System.out.println("Enter: tripId seatIndex name phone email paymentMethod(UPI/CARD/CASH)");
		String line = sc.nextLine().trim();
		String[] p = line.split("\\s+");
		long tripId = Long.parseLong(p[0]);
		int seatIndex = Integer.parseInt(p[1]);
		Passenger pas = new Passenger(p[2], p[3], p[4]);
		PaymentMethod method = PaymentMethod.valueOf(p[5].toUpperCase());

		reservationService.bookSeat(tripId, seatIndex, pas, method);

		// Backed collections demo: Arrays.asList fixed-size list view and subList
		Trip trip = tripRepo.find(tripId);
		if (trip != null) {
			List<Seat> seatView = Arrays.asList(trip.getSeats()); // fixed-size view
			int end = Math.min(5, seatView.size());
			List<Seat> window = seatView.subList(0, end);
			System.out.println("First 5 seats view (backed): " + window);
		}
	}

	private void handleCancel(Scanner sc) {
		System.out.println("Enter: bookingId");
		long bookingId = Long.parseLong(sc.nextLine().trim());
		boolean ok = reservationService.cancel(bookingId);
		System.out.println(ok ? "Cancelled." : "Already cancelled or not found.");
	}

	private void demoPassByValue() {
		int counter = 10;
		int updated = PassByValueDemo.incrementPrimitive(counter);
		System.out.println("Counter original=" + counter + ", updated(return)=" + updated);

		Passenger p = new Passenger("Alice", "9999999999", "alice@example.com");
		PassByValueDemo.mutatePassengerPhone(p);
		System.out.println("Passenger after mutate: " + p);

		// Reassign attempt doesn't affect caller
		PassByValueDemo.reassignPassenger(p);
		System.out.println("Passenger after reassign attempt: " + p);
	}

	private void demoTreeNavigation() {
		List<Trip> all = tripRepo.listAll();
		if (all.isEmpty()) {
			System.out.println("No trips.");
			return;
		}
		Date today = dateOnly(new Date());
		Date next = catalogService.nextAvailableDate(today);
		Date floor = catalogService.floorDate(today);
		System.out.println("Next date after today: " + next);
		System.out.println("Floor date (<= today): " + floor);
	}

	// ----------------- Helpers (Java 7) -----------------

	private Date parseDate(String s) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		return sdf.parse(s);
	}

	/**
	 * Validate "HH:mm" and return as-is (CatalogService sorts by lexicographic
	 * "HH:mm")
	 */
	private String parseTime(String s) {
		if (s == null || !s.matches("^\\d{2}:\\d{2}$")) {
			throw new IllegalArgumentException("Time must be HH:mm");
		}
		return s;
	}

	private Date addDays(Date date, int days) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		cal.add(java.util.Calendar.DATE, days);
		return cal.getTime();
	}

	/** Zero-out time part to keep date-only keys uniform in TreeMap */
	private Date dateOnly(Date dateTime) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(dateTime);
		cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
		cal.set(java.util.Calendar.MINUTE, 0);
		cal.set(java.util.Calendar.SECOND, 0);
		cal.set(java.util.Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
