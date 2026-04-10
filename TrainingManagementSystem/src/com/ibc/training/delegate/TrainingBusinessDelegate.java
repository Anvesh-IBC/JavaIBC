package com.ibc.training.delegate;

import com.ibc.training.domain.Course;
import com.ibc.training.domain.Enrollment;
import com.ibc.training.domain.Trainee;
import com.ibc.training.exception.TrainingException;
import com.ibc.training.service.TrainingService;

import java.util.List;

public class TrainingBusinessDelegate {
	private final TrainingService service;

	public TrainingBusinessDelegate(TrainingService service) {
		this.service = service;
	}

	public void enroll(String courseCode, Trainee trainee) throws TrainingException {
		service.enroll(courseCode, trainee);
	}

	public List<Course> listCourses() {
		return service.listCourses();
	}

	public List<Enrollment> listEnrollments(String courseCode) throws TrainingException {
		return service.listEnrollments(courseCode);
	}
}
