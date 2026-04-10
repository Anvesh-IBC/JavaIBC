package com.example.demos;

import java.util.*;

public class ArrayListBasicsDemo {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("Java");
		list.add("Spring");
		System.out.println("get(0): " + list.get(0));
		list.set(1, "Spring Boot");
		list.remove("Java");
		System.out.println("List: " + list);
	}
}
