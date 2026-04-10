package com.company.training;

public class DefaultTrainingService implements TrainingService {

	public void enroll(Employee e, Training t) {
		t.enroll(e);
		System.out.println("Employee " + e.getEmployeeId() + " enrolled in " + t.getTitle());
	}
}