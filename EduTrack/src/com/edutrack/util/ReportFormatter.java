package com.edutrack.util;

import com.edutrack.model.Result;
import com.edutrack.model.Student;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ReportFormatter {
	private ReportFormatter() {
	}

	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	private static final NumberFormat INR = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

	public static String resultCard(Student s, Result r) {
		StringBuilder sb = new StringBuilder(256);
		sb.append("===== Result Card =====\n").append("Name: ").append(s.getName()).append("\n").append("Email: ")
				.append(s.getEmail()).append("\n").append("Enrolled: ").append(DF.format(s.getEnrolledDate()))
				.append("\n").append("Fees Paid: ").append(s.isFeesPaid() ? "Yes" : "No").append("\n")
				.append("-----------------------\n").append("Marks: ").append(r.getMarks()).append("\n")
				.append("Percentage: ").append(String.format("%.2f", r.getPercentage())).append("\n").append("Grade: ")
				.append(r.getGrade()).append("\n");
		// Currency example (not used elsewhere)
		sb.append("Example Course Fee Formatting: ").append(INR.format(8000)).append("\n");
		return sb.toString();
	}
}
