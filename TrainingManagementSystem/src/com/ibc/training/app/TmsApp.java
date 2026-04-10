package com.ibc.training.app;

import com.ibc.training.dao.CourseDao;
import com.ibc.training.dao.InMemoryCourseDao;
import com.ibc.training.delegate.TrainingBusinessDelegate;
import com.ibc.training.domain.*;
import com.ibc.training.dto.CourseDTO;
import com.ibc.training.exception.TrainingException;
import com.ibc.training.iterator.CourseCatalog;
import com.ibc.training.iterator.CourseCatalogIterator;
import com.ibc.training.service.TrainingService;
import com.ibc.training.service.TrainingServiceImpl;
import com.ibc.training.util.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class TmsApp {
	public static void main(String[] args) {
		System.out.println("=== TRAINING MANAGEMENT SYSTEM CASE STUDY (Packages, Java 8) ===");

		// ---------- Enums, Static & Instance Initialization Blocks ----------
		CourseCatalog catalog = new CourseCatalog(); // instance init pre-populates defaults

		// ---------- DataTypes, Variables, Literals, Arrays ----------
		int maxDailyHours = 8;
		double gstRate = 0.18d;
		boolean isCorporate = true;
		char section = 'A';
		long idSeed = 1000L;
		int[] attendanceTemplate = new int[] { 1, 1, 1, 1, 0 };

		// ---------- Objects, Constructors & JavaBeans ----------
		Trainer trainer = new Trainer("TNR-101", "Rahul Sharma", "rahul@org.example");
		Trainee trainee1 = new Trainee();
		trainee1.setId("TRN-001");
		trainee1.setFullName("Ananya Gupta");
		trainee1.setEmail("ananya@example.com");

		Trainee trainee2 = new Trainee("TRN-002", "Arjun Mehta", "arjun@example.com");

		// ---------- Using enums ----------
		Course javaFundamentals = new Course("CRS-101", "Core Java", CourseLevel.BEGINNER, new BigDecimal("4999"), 24);
		Course advancedJava = new Course("CRS-201", "Advanced Java", CourseLevel.ADVANCED, new BigDecimal("8999"), 32);

		// ---------- Collections, Generics, Autoboxing ----------
		List<Trainee> trainees = new ArrayList<>();
		trainees.add(trainee1);
		trainees.add(trainee2);

		List<Integer> ratings = new ArrayList<>();
		ratings.add(5);
		ratings.add(4);
		ratings.add(Integer.valueOf(3));

		// ---------- Wrapper utilities, Parsing ----------
		int parsed = Integer.parseInt("42");
		Integer w = Integer.valueOf(parsed);
		double d = Double.parseDouble("3.14159");
		System.out.println("Parsed int=" + parsed + ", wrapper=" + w + ", double=" + d);

		// ---------- HashCode & Equals (on Course) ----------
		Course javaFundamentalsCopy = new Course("CRS-101", "Core Java", CourseLevel.BEGINNER, new BigDecimal("4999"),
				24);
		System.out.println("javaFundamentals equals copy? " + javaFundamentals.equals(javaFundamentalsCopy));
		Set<Course> uniqueCourses = new HashSet<>();
		uniqueCourses.add(javaFundamentals);
		uniqueCourses.add(javaFundamentalsCopy);
		System.out.println("Unique courses size: " + uniqueCourses.size());

		// ---------- DAO Pattern (In-Memory) ----------
		CourseDao courseDao = new InMemoryCourseDao();
		courseDao.save(javaFundamentals);
		courseDao.save(advancedJava);

		// ---------- Transfer Object ----------
		CourseDTO dto = CourseDTO.fromCourse(advancedJava);

		// ---------- Service Layer & Business Delegate ----------
		TrainingService service = new TrainingServiceImpl(courseDao);
		TrainingBusinessDelegate delegate = new TrainingBusinessDelegate(service);

		// ---------- Regex (Pattern Matching) for Validation ----------
		boolean validEmail = ValidationUtil.isValidEmail(trainee1.getEmail());
		boolean validCourseCode = ValidationUtil.isValidCourseCode(javaFundamentals.getCode());
		System.out.println("Email valid? " + validEmail + " | Course code valid? " + validCourseCode);

		// ---------- Exceptions ----------
		try {
			delegate.enroll("CRS-101", trainee1);
			delegate.enroll("CRS-101", trainee2);
			delegate.enroll("CRS-404", trainee1); // triggers TrainingException (course not found)
		} catch (TrainingException ex) {
			System.err.println("Enrollment error: " + ex.getMessage());
		}

		// ---------- Switch, If-Else, Operators, Loops ----------
		Course course = javaFundamentals;
		BigDecimal discount = isCorporate ? new BigDecimal("0.10") : new BigDecimal("0.00");
		BigDecimal finalFee = PricingUtil.applyGstAndDiscount(course.getFee(), gstRate, discount);
		System.out.println("Final fee for " + course.getTitle() + " (corporate? " + isCorporate + "): "
				+ FormattingUtil.formatCurrency(finalFee, new Locale("en", "IN")));

		// Classic switch (no arrow labels)
		switch (course.getLevel()) {
		case BEGINNER:
			System.out.println("Beginner-friendly curriculum.");
			break;
		case INTERMEDIATE:
			System.out.println("Requires prior Java knowledge.");
			break;
		case ADVANCED:
			System.out.println("Hands-on, performance-oriented.");
			break;
		default:
			break;
		}

		// For loop
		int total = 0;
		for (int i = 0; i < attendanceTemplate.length; i++) {
			total += attendanceTemplate[i];
		}
		System.out.println("Attendance template days: " + total);

		// While loop + break/continue
		int i = 0;
		while (i < trainees.size()) {
			Trainee t = trainees.get(i);
			if (t.getEmail().endsWith("@spam.example")) {
				i++;
				continue;
			}
			if (i == 5)
				break;
			i++;
		}

		// Do-while loop
		int retries = 0;
		do {
			retries++;
		} while (retries < 1);

		// ---------- Strings and StringBuilder/StringBuffer ----------
		String title = javaFundamentals.getTitle();
		System.out.println("Title length=" + title.length() + ", contains 'Java'? " + title.contains("Java"));

		String report = ReportUtil.buildEnrollmentReport(delegate, "CRS-101");
		System.out.println(report);

		StringBuffer sbuf = new StringBuffer("ThreadSafeBuffer:");
		sbuf.append(" ").append(java.time.LocalDateTime.now());
		System.out.println(sbuf.toString());

		// ---------- I/O: Save the report (Java 8 compatible) ----------
		try {
			java.nio.file.Path out = java.nio.file.Paths.get("tms_report.txt");
			Files.write(out, report.getBytes(StandardCharsets.UTF_8));
			System.out.println("Report saved to " + out.toAbsolutePath());
		} catch (IOException io) {
			System.err.println("Failed to save report: " + io.getMessage());
		}

		// ---------- Collections: Sorting, TreeMap/TreeSet navigation ----------
		List<Course> all = new ArrayList<>(delegate.listCourses());
		all.sort(Comparator.comparing(Course::getFee).thenComparing(Course::getTitle));
		System.out.println("Sorted courses by fee, then title: " + all);

		TreeSet<String> courseCodes = new TreeSet<>();
		for (Course c : all)
			courseCodes.add(c.getCode());
		System.out.println("First code=" + courseCodes.first() + ", last code=" + courseCodes.last()
				+ ", ceiling CRS-150: " + courseCodes.ceiling("CRS-150"));

		TreeMap<LocalDate, String> sessionPlan = new TreeMap<>();
		sessionPlan.put(LocalDate.now(), "Intro");
		sessionPlan.put(LocalDate.now().plusDays(1), "OO Basics");
		sessionPlan.put(LocalDate.now().plusDays(2), "Collections");
		System.out.println("FloorEntry(today+1): " + sessionPlan.floorEntry(LocalDate.now().plusDays(1)));
		System.out.println("HigherEntry(today): " + sessionPlan.higherEntry(LocalDate.now()));

		// ---------- Backed Collections ----------
		List<Course> backed = all.subList(0, Math.min(1, all.size()));
		if (!backed.isEmpty())
			backed.get(0).setTitle(backed.get(0).getTitle() + " (UPDATED)");
		System.out.println("Backed view changed first item title: " + all.get(0).getTitle());

		// ---------- Arrays: Declaration, Construction, Initialization ----------
		int[] hoursPerDay = new int[5];
		Arrays.fill(hoursPerDay, 2);
		System.out.println("Hours array: " + Arrays.toString(hoursPerDay));

		// ---------- Reference Casting, Polymorphism ----------
		Person p = trainer; // upcast
		if (p instanceof Trainer) {
			Trainer tr = (Trainer) p; // classic cast
			System.out.println("Polymorphic display: " + p.displayName());
			System.out.println("Trainer email: " + tr.getEmail());
		}

		// ---------- Pass-by-Value semantics ----------
		int primitive = 10;
		PassByValueDemo.modifyPrimitive(primitive);
		System.out.println("Primitive after modifyPrimitive: " + primitive); // unchanged

		Course mutableCourseRef = javaFundamentals;
		PassByValueDemo.modifyObjectField(mutableCourseRef);
		System.out.println("Course title after modifyObjectField: " + javaFundamentals.getTitle()); // changed

		PassByValueDemo.reassignObject(mutableCourseRef);
		System.out.println("Course title after reassignObject: " + javaFundamentals.getTitle()); // unchanged

		// ---------- Iterator Pattern (custom) ----------
		CourseCatalogIterator iterator = new CourseCatalogIterator(catalog);
		while (iterator.hasNext()) {
			Course c = iterator.next();
			if (c.getLevel() == CourseLevel.BEGINNER) {
				System.out.println("[Iterator] Beginner course: " + c.getCode() + " - " + c.getTitle());
			}
		}

		// ---------- Generics with Legacy Interop ----------
		List legacy = new ArrayList(); // raw type (legacy) - not recommended
		legacy.add("LegacyString");
		legacy.add(123);
		try {
			String s2 = (String) legacy.get(1); // will throw at runtime
		} catch (ClassCastException cce) {
			System.out.println("Caught ClassCastException from legacy list: " + cce.getMessage());
		}

		// ---------- Garbage Collection (concept) ----------
		Object temp = new Object();
		temp = null;
		System.out.println("Demo complete. (GC is automatic in Java)");
	}
}
