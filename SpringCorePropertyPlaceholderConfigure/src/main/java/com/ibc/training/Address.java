package com.ibc.training;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Address {
	@Value("No 15/4 Thiruvalluvar Street")
	private String addressLine1;
	@Value("Kalinjur,vellore-632006")
	private String addressLine2;

	public Address() {
		System.out.println("From the constructor of Address class\n");
	}

	public void setAddressLine1(String addressLine1) {
		System.out.println("From: Setter of Address Line1\n");
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine1() {
		return addressLine1;
	}
}