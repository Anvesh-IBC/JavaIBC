package com.edutrack.model;

public abstract class User {
	private int id;
	private String name;
	private String email;
	private long phone;

	public String getContactInfo() {
		return name + " | " + email + " | " + phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name != null ? name.trim() : "";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email != null ? email.trim() : "";
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
	}
}
