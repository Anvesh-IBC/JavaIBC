package com.example.demos;

import java.util.ArrayList;
import java.util.List;

public class AutoboxingDemo {
	public static void main(String[] args) {
		Integer boxed = 5; // autobox
		int sum = boxed + 10; // auto-unbox then arithmetic
		System.out.println("sum = " + sum);

		List<Integer> nums = new ArrayList<>();
		nums.add(1); // autobox
		nums.add(2);
		int first = nums.get(0); // auto-unbox
		System.out.println("first = " + first);

		Integer maybeNull = null;
		try {
			int x = maybeNull; // Unboxing null -> NPE
			System.out.println(x);
		} catch (NullPointerException npe) {
			System.out.println("Unboxing null throws NPE: " + npe);
		}
	}
}
