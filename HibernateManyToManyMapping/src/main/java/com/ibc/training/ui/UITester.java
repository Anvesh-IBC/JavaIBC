package com.ibc.training.ui;

import com.ibc.training.bean.EmployeeBean;
import com.ibc.training.bean.MeetingBean;
import com.ibc.training.service.EmployeeService;
import com.ibc.training.utility.Factory;

import java.util.*;
import java.util.function.Consumer;

public class UITester {

	private static final Scanner SCANNER = new Scanner(System.in);
	private static EmployeeService service;

	public static void main(String[] args) {
		try {
			service = Factory.createEmployeeService(); // your DI/factory
			runMenu();
		} catch (Exception e) {
			System.out.println("Fatal error initializing app: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Avoid closing System.in; just flush if needed
			System.out.println("Exiting. Goodbye!");
		}
	}

	private static void runMenu() {
		Map<Integer, Runnable> actions = new LinkedHashMap<>();
		actions.put(1, UITester::insertEmployeeAndMeetingsInteractive);
		actions.put(2, UITester::deleteEmployeeAndMeetingsInteractive);
		actions.put(3, UITester::insertSampleDataQuick); // optional quick demo
		actions.put(9, UITester::exitApp);

		while (true) {
			System.out.println("\n===== Employee & Meeting Console =====");
			System.out.println("1) Insert Employees & Meetings (interactive)");
			System.out.println("2) Delete Employee & Meetings (by Employee ID)");
			System.out.println("3) Insert Sample Data (quick)");
			System.out.println("9) Exit");
			System.out.print("Choose an option: ");

			int choice = readInt();
			Runnable action = actions.get(choice);

			if (action != null) {
				try {
					action.run();
				} catch (Exception e) {
					System.out.println("Operation failed: " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// === ACTIONS ===

	private static void insertEmployeeAndMeetingsInteractive() {
		System.out.println("\n--- Insert Employees & Meetings ---");

		EmployeeBean employee1 = new EmployeeBean();
		employee1.setEmployeeName(readNonEmpty("Enter Employee 1 Name: "));
		employee1.setRole(readNonEmpty("Enter Employee 1 Role: "));

		EmployeeBean employee2 = new EmployeeBean();
		employee2.setEmployeeName(readNonEmpty("Enter Employee 2 Name: "));
		employee2.setRole(readNonEmpty("Enter Employee 2 Role: "));

		MeetingBean m1 = new MeetingBean();
		m1.setTimeSlot(readNonEmpty("Enter Meeting 1 timeslot (e.g., 10:30AM to 11:30AM): "));

		MeetingBean m2 = new MeetingBean();
		m2.setTimeSlot(readNonEmpty("Enter Meeting 2 timeslot (e.g., 12:30PM to 1:00PM): "));

		try {
			service.insertEmployeeAndMeetings(employee1, employee2, m1, m2);
			System.out.println(" Employees and meetings inserted successfully!");
		} catch (Exception e) {
			System.out.println("Insert error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void deleteEmployeeAndMeetingsInteractive() {
		System.out.println("\n--- Delete Employee & Meetings ---");
		System.out.print("Enter Employee ID to delete: ");
		int employeeId = readInt();

		EmployeeBean employee = new EmployeeBean();
		employee.setEmployeeId(employeeId);

		try {
			service.deletingEmployeeAndMeeting(employee);
			System.out.println(" Employees and meetings deleted successfully!");
		} catch (Exception e) {
			System.out.println("Delete error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void insertSampleDataQuick() {
		System.out.println("\n--- Quick Sample Insert ---");
		EmployeeBean employee1 = new EmployeeBean();
		employee1.setEmployeeName("Sushma");
		employee1.setRole("Sr.Analyst");

		EmployeeBean employee2 = new EmployeeBean();
		employee2.setEmployeeName("Saisailaja");
		employee2.setRole("Specialist");

		MeetingBean m1 = new MeetingBean();
		m1.setTimeSlot("10:30AM to 11:30AM");

		MeetingBean m2 = new MeetingBean();
		m2.setTimeSlot("12:30PM to 1:00PM");

		try {
			service.insertEmployeeAndMeetings(employee1, employee2, m1, m2);
			System.out.println("Sample employees and meetings inserted successfully!");
		} catch (Exception e) {
			System.out.println("Insert error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void exitApp() {
		System.out.println("Thanks for using the app.");
		// Use System.exit to break outer loop cleanly
		System.exit(0);
	}

	// === INPUT HELPERS (Java 8 friendly) ===

	private static String readNonEmpty(String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = SCANNER.nextLine();
			if (input != null && !input.trim().isEmpty()) {
				return input.trim();
			}
			System.out.println("Input cannot be empty. Please try again.");
		}
	}

	private static int readInt() {
		while (true) {
			String line = SCANNER.nextLine();
			try {
				return Integer.parseInt(line.trim());
			} catch (NumberFormatException ex) {
				System.out.print("Please enter a valid number: ");
			}
		}
	}
}