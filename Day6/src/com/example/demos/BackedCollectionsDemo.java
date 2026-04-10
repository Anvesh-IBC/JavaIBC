package com.example.demos;

import java.util.*;

public class BackedCollectionsDemo {
	public static void main(String[] args) {
		String[] arr = { "Java", "Spring", "Docker" };
		List<String> backed = Arrays.asList(arr); // fixed-size list backed by array
		backed.set(0, "Java SE");
		System.out.println("Array: " + Arrays.toString(arr)); // reflects change

		// Java 7: Replace List.of() with Arrays.asList()
		List<String> list = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E"));
		List<String> view = list.subList(1, 4); // B..D view (indices 1,2,3)
		view.set(0, "B*");
		System.out.println("Parent list reflects subList change: " + list);

		// Caution: structural change on parent while view is active may cause
		// ConcurrentModificationException
	}
}
