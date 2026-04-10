package com.example.demos;

public class WrapperCreationDemo {
	public static void main(String[] args) {
		Integer i1 = Integer.valueOf(42);
		Double d1 = Double.valueOf(3.14);
		Boolean b1 = Boolean.valueOf(true);
		Character c1 = Character.valueOf('A');

		Integer i2 = Integer.parseInt("101");
		Double d2 = Double.parseDouble("2.71828");
		Boolean b2 = Boolean.parseBoolean("TrUe");

		// Constructors (legacy style): avoid; shown for completeness
		Integer i3 = new Integer(42);

		System.out.println("valueOf: " + i1 + ", " + d1 + ", " + b1 + ", " + c1);
		System.out.println("parse: " + i2 + ", " + d2 + ", " + b2);
		System.out.println("ctor: " + i3);
	}
}