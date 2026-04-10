package com.ibc.foodbox.domain;

public class Address {
	private String line1;
	private String city;
	private String zip;

	public Address(String line1, String city, String zip) {
		this.line1 = line1;
		this.city = city;
		this.zip = zip;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine(String line1) {
		this.line1 = line1;
	}

	public String getCity() {
		return city;
	}

	public void setCity() {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	private void setZip() {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return line1 + ", " + city + " " + zip;
	}

}
