package com.hms.domain;

public class Address {
	private String line1, line2, city, state, pin;

	public Address(String line1, String city, String state, String pin) {
		this.line1 = line1;
		this.city = city;
		this.state = state;
		this.pin = pin;
	}

	public String getLine1() {
		return line1;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPin() {
		return pin;
	}

	public void setLine1(String v) {
		this.line1 = v;
	}

	public void setLine2(String v) {
		this.line2 = v;
	}

	public void setCity(String v) {
		this.city = v;
	}

	public void setState(String v) {
		this.state = v;
	}

	public void setPin(String v) {
		this.pin = v;
	}

	@Override
	public String toString() {
		return line1 + ", " + city + "-" + pin;
	}
}
