package com.example.demos;

import java.util.*;

public class CollectionsBasicsDemo {
	public static void main(String[] args) {
		// List
		List<String> titles = new ArrayList<>();
		titles.add("Java");
		titles.add("Spring");
		titles.add("Java"); // duplicate allowed

		// Set
		Set<String> skills = new HashSet<>(titles); // removes duplicate automatically

		// Map
		Map<Integer, String> byId = new HashMap<>();
		byId.put(101, "Java");
		byId.put(102, "Spring");

		// Queue
		Queue<String> tasks = new LinkedList<>();
		tasks.offer("Enroll");
		tasks.offer("Pay");
		tasks.offer("Notify");

		System.out.println("List: " + titles);
		System.out.println("Set: " + skills);
		System.out.println("Map: " + byId);
		while (!tasks.isEmpty())
			System.out.println("Queue poll: " + tasks.poll());

		// Collections helpers
		Collections.sort(titles);
		Collections.reverse(titles);
		System.out.println("Sorted & reversed titles: " + titles);
	}
}
