package com.example.demos;

import java.util.HashSet;
import java.util.Set;

public class EqualityHashDemo {
	public static void main(String[] args) {
		Set<CourseEntity> set = new HashSet<>();
		set.add(new CourseEntity(101, "Java", 30));
		set.add(new CourseEntity(101, "Java", 30)); // duplicate by value
		System.out.println("Set size (should be 1): " + set.size());
		System.out.println(set);
	}
}