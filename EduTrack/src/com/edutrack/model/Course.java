package com.edutrack.model;

public class Course {
	private int courseId;
	private String courseName;
	private short durationMonths;
	private double fees;
	private boolean active = true;

	public Course() {
	}

	public Course(int courseId, String courseName, short durationMonths, double fees) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.durationMonths = durationMonths;
		this.fees = fees;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public short getDurationMonths() {
		return durationMonths;
	}

	public void setDurationMonths(short durationMonths) {
		this.durationMonths = durationMonths;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Course{id=" + courseId + ", name='" + courseName + "', duration=" + durationMonths + "m, fees=" + fees
				+ "}";
	}
}
