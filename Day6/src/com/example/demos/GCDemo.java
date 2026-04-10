package com.example.demos;

public class GCDemo {
	public static void main(String[] args) {
		Integer big = Integer.valueOf(1000);
		big = null; // object eligible for GC
		System.gc(); // hint only; not guaranteed
		System.out.println("Requested GC; program continues...");
	}
}
