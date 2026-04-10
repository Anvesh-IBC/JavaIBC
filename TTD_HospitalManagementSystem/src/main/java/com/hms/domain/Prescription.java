package com.hms.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Prescription {
	private final String id;
	private final List<Medication> meds = new ArrayList<>();

	public Prescription(String id) {
		this.id = id;
	}

	public void addMedication(Medication m) {
		meds.add(m);
	}

	public List<Medication> getMedications() {
		return Collections.unmodifiableList(meds);
	}
}
