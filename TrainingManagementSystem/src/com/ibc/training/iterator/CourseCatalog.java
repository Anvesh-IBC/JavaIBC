package com.ibc.training.iterator;

import com.ibc.training.domain.Course;
import com.ibc.training.domain.CourseLevel;

import java.math.BigDecimal;
import java.util.*;

public class CourseCatalog implements Iterable<Course> {
	private final List<Course> items = new ArrayList<>();

	{ // instance init preload
		items.add(new Course("CRS-001", "Intro to Programming", CourseLevel.BEGINNER, new BigDecimal("1999"), 12));
		items.add(new Course("CRS-101", "Core Java", CourseLevel.BEGINNER, new BigDecimal("4999"), 24));
		items.add(new Course("CRS-201", "Advanced Java", CourseLevel.ADVANCED, new BigDecimal("8999"), 32));
	}

	public void add(Course c) {
		items.add(c);
	}

	public List<Course> asList() {
		return Collections.unmodifiableList(items);
	}

	@Override
	public Iterator<Course> iterator() {
		return items.iterator();
	}
}
