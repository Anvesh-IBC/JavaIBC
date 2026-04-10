package com.example.demos;

import java.util.*;

public class AutoboxingCollectionsDemo {
	public static void main(String[] args) {
		List<Integer> durations = new ArrayList<>();
		durations.add(30); // autobox
		durations.add(45);
		int total = 0;
		for (int d : durations)
			total += d; // auto-unbox
		System.out.println("Total duration: " + total);
	}
}
