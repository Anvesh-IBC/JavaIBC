package com.ibc.training.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Course {
	private static int COURSE_COUNT = 0;
	public static final BigDecimal DEFAULT_FEE = new BigDecimal("2999");

	{
		COURSE_COUNT++;
	} // instance init
	static {
		/* static init if needed */ }

	private String code;
	private String title;
	private CourseLevel level;
	private BigDecimal fee;
	private int hours;

	public Course() {
	}

	public Course(String code, String title) {
		this(code, title, CourseLevel.BEGINNER, DEFAULT_FEE, 16);
	}

	public Course(String code, String title, CourseLevel level, BigDecimal fee, int hours) {
		this.code = Objects.requireNonNull(code);
		this.title = Objects.requireNonNull(title);
		this.level = Objects.requireNonNull(level);
		this.fee = Objects.requireNonNull(fee);
		this.hours = hours;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = Objects.requireNonNull(title);
	}

	public CourseLevel getLevel() {
		return level;
	}

	public void setLevel(CourseLevel level) {
		this.level = level;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Course))
			return false;
		Course c = (Course) o;
		return Objects.equals(code, c.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public String toString() {
		return code + " (" + title + ", " + level + ", fee=" + fee + ", hours=" + hours + ")";
	}

	public static int getCourseCount() {
		return COURSE_COUNT;
	}
}