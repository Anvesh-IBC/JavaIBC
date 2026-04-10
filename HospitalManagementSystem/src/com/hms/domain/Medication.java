package com.hms.domain;

public class Medication {
	private final String name;
	private final String dosage;

	public Medication(String name, String dosage) {
		this.name = name;
		this.dosage = dosage;
	}

	public String getName() {
		return name;
	}

	public String getDosage() {
		return dosage;
	}
}
