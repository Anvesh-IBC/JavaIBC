package com.ibc.training.util;

import com.ibc.training.delegate.TrainingBusinessDelegate;
import com.ibc.training.domain.Enrollment;
import com.ibc.training.exception.TrainingException;

import java.time.format.DateTimeFormatter;
import java.util.List;

public final class ReportUtil {
	private ReportUtil() {
	}

	public static String buildEnrollmentReport(TrainingBusinessDelegate delegate, String courseCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Enrollment Report for ").append(courseCode).append(" ===\n");
		try {
			List<Enrollment> list = delegate.listEnrollments(courseCode);
			sb.append("Total Enrollments: ").append(list.size()).append("\n");
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			for (Enrollment e : list) {
				sb.append(" - Trainee: ").append(e.getTraineeId()).append(", Enrolled At: ")
						.append(e.getEnrolledAt().format(fmt)).append("\n");
			}
		} catch (TrainingException ex) {
			sb.append("ERROR: ").append(ex.getMessage()).append("\n");
		}
		return sb.toString();
	}
}