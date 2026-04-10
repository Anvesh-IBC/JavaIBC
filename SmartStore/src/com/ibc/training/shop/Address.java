package com.ibc.training.shop;

public class Address {
	private final String line1;
	private final String city;
	private final String state;
	private final String zip;

	public Address(String line1, String city, String state, String zip) {
		this.line1 = line1;
		this.city = city;
		this.state = state;
		this.zip = zip;
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

	public String getZip() {
		return zip;
	}

	@Override
	public String toString() {
		return line1 + ", " + city + ", " + state + " " + zip;
	}
}