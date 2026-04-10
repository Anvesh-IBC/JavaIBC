package com.example.demos;

public class WrapperUtilsDemo {
	public static void main(String[] args) {
		int n = Integer.parseInt("2A", 16); // hex -> 42
		String s = Integer.toString(42, 2); // binary
		int cmp = Integer.compare(10, 20); // -1
		System.out.printf("hex 2A -> %d, 42 -> bin: %s, compare(10,20): %d%n", n, s, cmp);
		System.out.println("Integer MIN..MAX: " + Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE);
	}
}
