package com.hms.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Department implements Iterable<Doctor> {
	private final String id;
	private String name;
	private final List<Doctor> doctors = new ArrayList<>();

	public Department(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addDoctor(Doctor d) {
		doctors.add(d);
	}

	public List<Doctor> getDoctors() {
		return Collections.unmodifiableList(doctors);
	}

	@Override
	public Iterator<Doctor> iterator() {
		return doctors.iterator();
	}
}