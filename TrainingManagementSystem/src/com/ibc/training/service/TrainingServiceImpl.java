package com.ibc.training.service;

import com.ibc.training.dao.CourseDao;
import com.ibc.training.domain.Course;
import com.ibc.training.domain.Enrollment;
import com.ibc.training.domain.Trainee;
import com.ibc.training.exception.TrainingException;
import com.ibc.training.util.ValidationUtil;

import java.util.*;

public class TrainingServiceImpl implements TrainingService {
	private final CourseDao courseDao;
	private final Map<String, List<Enrollment>> enrollmentsByCourse = new HashMap<>();

	public TrainingServiceImpl(CourseDao dao) {
		this.courseDao = dao;
	}

	@Override
	public void enroll(String courseCode, Trainee trainee) throws TrainingException {
		Course course = courseDao.findByCode(courseCode)
				.orElseThrow(() -> new TrainingException("Course not found: " + courseCode));

		if (!ValidationUtil.isValidEmail(trainee.getEmail())) {
			throw new TrainingException("Invalid trainee email for " + trainee.getFullName());
		}

		enrollmentsByCourse.computeIfAbsent(courseCode, k -> new ArrayList<>())
				.add(new Enrollment(courseCode, trainee.getId()));
	}

	@Override
	public List<Course> listCourses() {
		return courseDao.findAll();
	}

	@Override
	public List<Enrollment> listEnrollments(String courseCode) throws TrainingException {
		if (!courseDao.exists(courseCode))
			throw new TrainingException("Unknown course: " + courseCode);
		return enrollmentsByCourse.getOrDefault(courseCode, Collections.emptyList());
	}
}
