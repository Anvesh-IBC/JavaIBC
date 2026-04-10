package demos;

public class StringDemo {

	public static void main(String[] args) {

		// String creation
		String s1 = "Java"; // String literal
		String s2 = new String("Java"); // using new keyword

		// Printing strings
		System.out.println("s1 value: " + s1);
		System.out.println("s2 value: " + s2);

		// String methods
		System.out.println("Length of s1: " + s1.length());
		System.out.println("Char at index 1: " + s1.charAt(1));

		// Comparison
		System.out.println("Equals method: " + s1.equals(s2));
		System.out.println("== operator: " + (s1 == s2));

		// Other String methods
		System.out.println("Substring (1,3): " + s1.substring(1, 3));
		System.out.println("Uppercase: " + s1.toUpperCase());

		// Demonstrating immutability
		s1.concat(" World");
		System.out.println("After concat() without assignment: " + s1);

		s1 = s1.concat(" World");
		System.out.println("After concat() with assignment: " + s1);
	}
}