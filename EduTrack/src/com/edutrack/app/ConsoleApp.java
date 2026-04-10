package com.edutrack.app;

import com.edutrack.exception.EntityNotFoundException;
import com.edutrack.exception.InvalidStudentDataException;
import com.edutrack.model.Result;
import com.edutrack.model.Student;
import com.edutrack.service.*;
import com.edutrack.util.ReportFormatter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

	private final StudentService studentService = new StudentService();
	private final MarksService marksService = new MarksService();
	private final AttendanceService attendanceService = new AttendanceService();
	private final CourseService courseService = new CourseService(); // shows arrays -> List migration
	private final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		new ConsoleApp().run();
	}

	public void run() {
		System.out.println("=== EduTrack (Java 8) ===");
		boolean exit = false;
		while (!exit) {
			printMenu();
			String choice = sc.nextLine().trim();
			switch (choice) {
			case "1":
				addStudentFlow();
				break;
			case "2":
				listStudentsFlow();
				break;
			case "3":
				enterMarksFlow();
				break;
			case "4":
				viewResultFlow();
				break;
			case "5":
				attendanceFlow();
				break;
			case "6":
				passByValueDemo();
				break;
			case "7":
				showCoursesFlow();
				break;
			case "8":
				exit = true;
				System.out.println("Bye!");
				break;
			default:
				System.out.println("Invalid choice.");
			}
		}
	}

	private void printMenu() {
		System.out.println("\n1) Add Student");
		System.out.println("2) List Students");
		System.out.println("3) Enter Marks (3 subjects)");
		System.out.println("4) View Result Card");
		System.out.println("5) Mark/View Attendance %");
		System.out.println("6) Pass-by-Value Demo");
		System.out.println("7) Show Courses (array -> list)");
		System.out.println("8) Exit");
		System.out.print("Enter choice: ");
	}

	private void addStudentFlow() {
		try {
			System.out.print("Name: ");
			String name = sc.nextLine();
			System.out.print("Email: ");
			String email = sc.nextLine();
			System.out.print("Phone (10 digits): ");
			String phoneStr = sc.nextLine();
			System.out.print("Age (>=16): ");
			byte age = Byte.parseByte(sc.nextLine());

			Student s = studentService.addStudent(name, email, phoneStr, age);
			System.out.println("Added: " + s);
		} catch (InvalidStudentDataException e) {
			System.out.println("Validation failed: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Invalid number entered.");
		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
		}
	}

	private void listStudentsFlow() {
		try {
			List<Student> all = studentService.listAll();
			if (all.isEmpty()) {
				System.out.println("No students found.");
				return;
			}
			for (Student s : all)
				System.out.println(s);
		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
		}
	}

	private void enterMarksFlow() {
		try {
			System.out.print("Student ID: ");
			int id = Integer.parseInt(sc.nextLine());
			System.out.print("Marks (m1 m2 m3): ");
			String[] parts = sc.nextLine().trim().split("\\s+");
			if (parts.length != 3) {
				System.out.println("Need exactly 3 numbers.");
				return;
			}
			int m1 = Integer.parseInt(parts[0]);
			int m2 = Integer.parseInt(parts[1]);
			int m3 = Integer.parseInt(parts[2]);
			marksService.addMarks(id, m1, m2, m3);
			Result r = marksService.getResult(id);
			System.out.println("Saved. Result: " + r);
		} catch (NumberFormatException e) {
			System.out.println("Invalid number.");
		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
		} catch (EntityNotFoundException e) {
			System.out.println("Not found: " + e.getMessage());
		} catch (InvalidStudentDataException e) {
		    System.out.println("Invalid student data: " + e.getMessage());
		}
	}

	private void viewResultFlow() {
		try {
			System.out.print("Student ID: ");
			int id = Integer.parseInt(sc.nextLine());
			Result r = marksService.getResult(id);
			Student s = studentService.getById(id);
			System.out.println(ReportFormatter.resultCard(s, r));
		} catch (NumberFormatException e) {
			System.out.println("Invalid number.");
		} catch (EntityNotFoundException e) {
			System.out.println("Not found: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
		}
	}

	private void attendanceFlow() {
		try {
			System.out.print("1) Mark  2) Percentage : ");
			String ch = sc.nextLine().trim();
			if ("1".equals(ch)) {
				System.out.print("Student ID: ");
				int id = Integer.parseInt(sc.nextLine());
				System.out.print("Date (YYYY-MM-DD): ");
				LocalDate d = LocalDate.parse(sc.nextLine());
				System.out.print("Present? (true/false): ");
				boolean p = Boolean.parseBoolean(sc.nextLine());
				attendanceService.mark(id, d, p);
				System.out.println("Marked.");
			} else if ("2".equals(ch)) {
				System.out.print("Student ID: ");
				int id = Integer.parseInt(sc.nextLine());
				System.out.print("From (YYYY-MM-DD): ");
				LocalDate f = LocalDate.parse(sc.nextLine());
				System.out.print("To (YYYY-MM-DD): ");
				LocalDate t = LocalDate.parse(sc.nextLine());
				double pct = attendanceService.percentage(id, f, t);
				System.out.printf("Attendance %%: %.2f%%%n", pct);
			} else
				System.out.println("Invalid.");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Java is pass-by-value: primitives copy; object refs copy (mutations visible,
	// reassignment not).
	private void passByValueDemo() {
		Student s = new Student();
		s.setId(999);
		s.setName("Demo");
		System.out.println("Before: " + s.getName());
		mutateName(s); // mutates internal field (visible outside)
		System.out.println("After mutateName: " + s.getName());
		reassignRef(s); // reassigns parameter (caller unaffected)
		System.out.println("After reassignRef: " + s.getName());

		// Demonstrate uninitialized local (compile-time) — uncomment to see:
		// int x; System.out.println(x); // error: variable x might not have been
		// initialized
	}

	private void mutateName(Student s) {
		s.setName("Changed Inside");
	}

	private void reassignRef(Student s) {
		s = new Student();
		s.setName("New Object");
	}

	private void showCoursesFlow() {
		System.out.println("Default courses seeded via array, then migrated to List:");
		courseService.getAllCourses().forEach(System.out::println);
	}
}