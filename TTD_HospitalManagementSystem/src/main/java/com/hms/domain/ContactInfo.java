package com.hms.domain;

public class ContactInfo {
	private String phone;
	private String email;

	public ContactInfo(String phone, String email) {
		this.phone = phone;
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public void setPhone(String v) {
		this.phone = v;
	}

	public void setEmail(String v) {
		this.email = v;
	}
}