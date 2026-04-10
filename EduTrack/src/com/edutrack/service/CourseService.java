package com.edutrack.service;

import com.edutrack.model.Course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseService {
	// Array Declaration + Construction + Initialization
	private final Course[] defaultCourses = new Course[] { 
			new Course(101, "Java Fundamentals", (short) 3, 8000.0),
			new Course(102, "TypeScript", (short) 2, 5000.0), 
			new Course(103, "Angular Basics", (short) 2, 7000.0) 
	};

	// Migrate to List (dynamic)
	private final List<Course> courses = new ArrayList<>(Arrays.asList(defaultCourses));

	public List<Course> getAllCourses() {
		return courses;
	}
}