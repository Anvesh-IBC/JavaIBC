package com.example.demos;

import java.util.*;

public class TreeNavigationDemo {
	public static void main(String[] args) {
		// Java 7: Replace List.of() with Arrays.asList()
		Integer[] capacitiesArray = { 10, 20, 30, 40 };
		TreeSet<Integer> capacities = new TreeSet<Integer>(Arrays.asList(capacitiesArray));

		System.out.println("First: " + capacities.first());
		System.out.println("Higher than 25: " + capacities.higher(25));
		System.out.println("Floor of 25: " + capacities.floor(25));

		TreeMap<Integer, String> courseMap = new TreeMap<Integer, String>();
		courseMap.put(101, "Java");
		courseMap.put(104, "Spring");
		courseMap.put(103, "Docker");

		System.out.println("Next key > 101: " + courseMap.higherKey(101));
		System.out.println("Sub-map 101..103: " + courseMap.subMap(101, true, 103, true));
	}
}
