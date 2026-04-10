package com.ibc.training.domain;

public class Trainer extends Person {
	public Trainer(String id, String fullName, String email) {
		super(id, fullName, email);
	}

	public String displayName(String prefix) {
		return prefix + " " + displayName();
	}
}
