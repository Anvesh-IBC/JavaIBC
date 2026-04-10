package com.example.app;

import java.util.Scanner;

/** Optional console app demonstrating usage of StringCalculator. */

public class Main {
	public static void main(String[] args) {
		StringCalculator calc = new StringCalculator();
		System.out.println("Enter comma-separated integers (e.g., 1,2,3). Empty to exit.");
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.println(">");
				String line = sc.nextLine();
				if (line == null || line.trim().isEmpty()) {
					System.out.println("Goodbye!");
					break;
				}
				try {
					int result = calc.add(line);
					System.out.println("Sum = " + result);
				} catch (IllegalArgumentException ex) {
					System.out.println("Error: " + ex.getMessage());
				}
			}
		}
	}
}
