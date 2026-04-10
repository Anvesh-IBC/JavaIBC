package com.example.app;

import java.util.Objects;

/**
 * A simple core Java utility that sums comma-separated integers in a string.
 * Examples: - "1,2,3" => 6 - " 10 , -5 , 2 " => 7 - "" or null => 0
 *
 * Invalid inputs (non-numeric tokens) throw IllegalArgumentException.
 */

public class StringCalculator {
	/**
	 * Sums comma-separated integer tokens. Whitespace around tokens is ignored.
	 * 
	 * @param input e.g. "1, 2, 3"
	 * @return sum of the integers; 0 for null/empty
	 * @throws IllegalArgumentException if a token is not a valid integer
	 */

	public int add(String input) {
		if (input == null || input.trim().isEmpty()) {
			return 0;
		}

		int sum = 0;
		String[] parts = input.split(",");
		for (String part : parts) {
			String token = part.trim();
			if (token.isEmpty()) {
				// treat empty token as 0 (optional behavior; could also reject)
				continue;
			}

			try {
				sum += Integer.parseInt(token);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("Invalid number: '" + token + "'", nfe);
			}
		}
		return sum;
	}
}
