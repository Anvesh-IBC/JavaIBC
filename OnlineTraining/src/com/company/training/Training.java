package com.company.training;

public class Training {

	private String title;
	private Employee[] participants;
	private int count;

	static {
		System.out.println("Training class loaded");
	}

	{
		System.out.println("New Training created");
	}

	public Training(String title) {
		this.title = title;
		participants = new Employee[10];
	}

	public void enroll(Employee e) {
		participants[count++] = e;
		e.addTraining(this);
	}

	public void showParticipants() {
		System.out.println("Training: " + title);
		for (int i = 0; i < count; i++) {
			System.out.println(participants[i].getEmployeeId());
		}
	}

	public String getTitle() {
		return title;
	}
}
