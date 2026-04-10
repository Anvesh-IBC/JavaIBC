package com.brs.model;

/** Passenger extends Person */

public class Passenger extends Person {
	private String email;

	//public Passenger() {}

	public Passenger(String name, String phone, String email) {
		super(name, phone);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Passenger{name='" + getName() + "', phone='" + getPhone() + "', email='" + email + "'}";
	}
}
