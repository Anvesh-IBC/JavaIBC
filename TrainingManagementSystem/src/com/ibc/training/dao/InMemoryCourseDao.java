package com.ibc.training.dao;

import com.ibc.training.domain.Course;

import java.util.*;

public class InMemoryCourseDao implements CourseDao {
	private final Map<String, Course> db = new HashMap<>();

	@Override
	public void save(Course course) {
		db.put(course.getCode(), course);
	}

	@Override
	public Optional<Course> findByCode(String code) {
		return Optional.ofNullable(db.get(code));
	}

	@Override
	public List<Course> findAll() {
		return new ArrayList<>(db.values());
	}

	@Override
	public boolean exists(String code) {
		return db.containsKey(code);
	}
}
