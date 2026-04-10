package com.example.demos;

import java.util.*;

public class OtherNavigationDemo {
	public static void main(String[] args) {
		// Java 7: Replace List.of() with Arrays.asList()
		List<String> titles = new ArrayList<String>(Arrays.asList("Java", "Spring", "Docker"));

		// Iterator - works exactly same in Java 7
		Iterator<String> it = titles.iterator();
		while (it.hasNext()) {
			System.out.println("Iterator: " + it.next());
		}

		// ListIterator (bidirectional) - works exactly same in Java 7
		ListIterator<String> li = titles.listIterator(titles.size());
		while (li.hasPrevious()) {
			System.out.println("ListIterator backwards: " + li.previous());
		}

		// Streams REMOVED - Java 8+ only, replaced with Java 7 loop
		for (String s : titles) {
			if (s.startsWith("D")) {
				System.out.println("Filter D*: " + s);
			}
		}
	}
}