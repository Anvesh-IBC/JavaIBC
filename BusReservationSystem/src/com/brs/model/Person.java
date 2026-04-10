package com.brs.model;

import java.util.Objects;

/** Base class for people (demonstrates inheritance) */

public class Person {
	private String name;
	private String phone;

	public Person() {
	}

	public Person(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Person{name='" + name + "', phone='" + phone + "'}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Person))
			return false;
		Person person = (Person) o;
		return Objects.equals(name, person.name) && Objects.equals(phone, person.phone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, phone);
	}
}
