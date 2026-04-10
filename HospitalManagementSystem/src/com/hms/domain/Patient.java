package com.hms.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patient {
	private final String id;
	private String name;
	private Gender gender;
	private LocalDate dob;
	private Address address;
	private ContactInfo contactInfo;
	private final List<Appointment> appointments = new ArrayList<>();

	public Patient(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender g) {
		this.gender = g;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate d) {
		this.dob = d;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address a) {
		this.address = a;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo c) {
		this.contactInfo = c;
	}

	public List<Appointment> getAppointments() {
		return Collections.unmodifiableList(appointments);
	}

	void addAppointment(Appointment a) {
		appointments.add(a);
	}

//	@Override
//	public String toString() {
//		return "Patient [id=" + id + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", address=" + address
//				+ ", contactInfo=" + contactInfo + ", appointments=" + appointments + "]";
//	}

	@Override
	public String toString() {
		// Option A: show only a count (simplest & safest)
		int count = appointments == null ? 0 : appointments.size();
		return "Patient{id=" + id + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", address=" + address
				+ ", contactInfo=" + contactInfo + ", appointmentsCount=" + count + '}';
	};

	@Override
	public boolean equals(Object o) {
		return o instanceof Patient && ((Patient) o).id.equals(this.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
