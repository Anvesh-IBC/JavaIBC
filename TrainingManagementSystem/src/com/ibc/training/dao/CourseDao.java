package com.ibc.training.dao;

import com.ibc.training.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
	void save(Course course);

	Optional<Course> findByCode(String code);

	List<Course> findAll();

	boolean exists(String code);
}
