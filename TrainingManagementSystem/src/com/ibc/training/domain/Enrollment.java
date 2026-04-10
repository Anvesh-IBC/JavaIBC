package com.ibc.training.domain;

import java.time.LocalDateTime;

public class Enrollment {
	private final String courseCode;
	private final String traineeId;
	private final LocalDateTime enrolledAt = LocalDateTime.now();

	public Enrollment(String courseCode, String traineeId) {
		this.courseCode = courseCode;
		this.traineeId = traineeId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getTraineeId() {
		return traineeId;
	}

	public LocalDateTime getEnrolledAt() {
		return enrolledAt;
	}
}
