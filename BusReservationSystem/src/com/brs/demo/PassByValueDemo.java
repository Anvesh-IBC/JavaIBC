package com.brs.demo;

import com.brs.model.Passenger;

/** Shows Java's pass-by-value semantics */
public class PassByValueDemo {
	public static int incrementPrimitive(int counter) {
		counter = counter + 1; // primitive updated locally
		return counter; // caller must capture return value
	}

	public static void mutatePassengerPhone(Passenger p) {
		// Java passes "reference by value"; we mutate the object the reference points
		// to.
		p.setPhone(p.getPhone() + " (updated)");
	}

	public static void reassignPassenger(Passenger p) {
		// Reassigning local reference doesn't affect caller's reference
		p = new Passenger("New Name", "0000000000", "new@example.com");
	}
}