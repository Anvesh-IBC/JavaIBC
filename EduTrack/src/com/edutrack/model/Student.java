package com.edutrack.model;

import java.time.LocalDate;
import java.util.Objects;

public class Student extends User {
	private byte age;
	private LocalDate enrolledDate;
	private boolean feesPaid;
	private Result result;

	// Instance initialization block
	{
		this.feesPaid = false;
	}

	public Student() {
		this.enrolledDate = LocalDate.now();
	}

	public Student(int id, String name, String email, long phone, byte age) {
		setId(id);
		setName(name);
		setEmail(email);
		setPhone(phone);
		this.age = age;
		this.enrolledDate = LocalDate.now();
		this.feesPaid = false;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	public LocalDate getEnrolledDate() {
		return enrolledDate;
	}

	public void setEnrolledDate(LocalDate enrolledDate) {
		this.enrolledDate = enrolledDate;
	}

	public boolean isFeesPaid() {
		return feesPaid;
	}

	public void setFeesPaid(boolean feesPaid) {
		this.feesPaid = feesPaid;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void updateFeesPaid(boolean paid) {
		this.feesPaid = paid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Student))
			return false;
		Student s = (Student) o;
		return getId() == s.getId(); // equality by id
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return "Student{id=" + getId() + ", name='" + getName() + "', email='" + getEmail() + "', age=" + age
				+ ", enrolled=" + enrolledDate + ", feesPaid=" + feesPaid + "}";
	}
}