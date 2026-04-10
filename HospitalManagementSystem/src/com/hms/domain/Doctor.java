package com.hms.domain;

import java.util.EnumSet;

public class Doctor {
	private final String id;
	private String name;
	private final EnumSet<Specialization> specializations = EnumSet.noneOf(Specialization.class);

	public Doctor(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public void addSpecialization(Specialization s) {
		specializations.add(s);
	}

	public EnumSet<Specialization> getSpecializations() {
		return specializations.clone();
	}
	

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", specializations=" + specializations + "]";
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Doctor && ((Doctor) o).id.equals(this.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
