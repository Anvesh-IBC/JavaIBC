package com.ibc.training.service;

import com.ibc.training.domain.Course;
import com.ibc.training.domain.Enrollment;
import com.ibc.training.domain.Trainee;
import com.ibc.training.exception.TrainingException;

import java.util.List;

public interface TrainingService {
	void enroll(String courseCode, Trainee trainee) throws TrainingException;

	List<Course> listCourses();

	List<Enrollment> listEnrollments(String courseCode) throws TrainingException;
}
