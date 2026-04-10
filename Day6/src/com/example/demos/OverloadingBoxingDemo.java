package com.example.demos;

public class OverloadingBoxingDemo {
	static void f(int x) {
		System.out.println("primitive int chosen: " + x);
	}

	static void f(Integer x) {
		System.out.println("wrapper Integer chosen: " + x);
	}

	static void f(long x) {
		System.out.println("widening to long: " + x);
	}

	public static void main(String[] args) {
		Integer obj = 10;
		f(obj); // wrapper overload selected
		f(10); // primitive overload selected
		short s = 5;
		f(s); // primitive short -> matches int better than long
	}
}