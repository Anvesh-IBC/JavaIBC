package VariablesInitialization;

public class UninitializedDemo {
	public static void main(String[] args) {
		int[] marks = new int[5]; // all elements are 0 by default
		System.out.println("First mark: " + marks[0]); // 0

		int num;
		// Uncommenting below line causes error: variable num might not have been
		// initialized
		// System.out.println(num);
	}
}
