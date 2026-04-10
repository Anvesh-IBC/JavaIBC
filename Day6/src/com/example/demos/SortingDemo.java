package com.example.demos;

import java.util.*;

public class SortingDemo {
	public static void main(String[] args) {
		List<CourseEntity> list = new ArrayList<CourseEntity>();
		list.add(new CourseEntity(101, "Java", 30));
		list.add(new CourseEntity(102, "Spring Boot", 45));
		list.add(new CourseEntity(103, "Docker", 20));

		// Sort by duration ascending (Java 7 anonymous comparator)
		Collections.sort(list, new Comparator<CourseEntity>() {
			@Override
			public int compare(CourseEntity a, CourseEntity b) {
				return Integer.compare(a.getDuration(), b.getDuration());
			}
		});
		System.out.println("By duration asc: " + list);

		// Sort by title descending (Java 7 anonymous comparator)
		Collections.sort(list, new Comparator<CourseEntity>() {
			@Override
			public int compare(CourseEntity a, CourseEntity b) {
				return b.getTitle().compareTo(a.getTitle());
			}
		});
		System.out.println("By title desc: " + list);

		// Array sort (works in Java 7)
		int[] arr = { 3, 1, 2 };
		Arrays.sort(arr);
		System.out.println("Sorted array: " + Arrays.toString(arr));
	}
}
