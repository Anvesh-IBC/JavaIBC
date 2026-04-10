package com.company.training;

public class Trainer extends Employee {

	private String expertise;

	public Trainer(int id, String name, String expertise) {
		super(id, name);
		this.expertise = expertise;
	}

	public void train() {
		System.out.println("Trainer teaches: " + expertise);
	}
}
