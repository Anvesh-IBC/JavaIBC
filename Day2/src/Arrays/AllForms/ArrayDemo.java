package Arrays.AllForms;

public class ArrayDemo {
	public static void main(String[] args) {
		// 1. DECLARATION (reference only)
		int[] arr1; // OK
		// int[] arr2 = new int[5]; // Declaration + Construction

		// 2. CONSTRUCTION (allocates memory)
		int[] arr2 = new int[5]; // [0,0,0,0,0]

		// 3. INITIALIZATION
		int[] arr3 = { 1, 2, 3 }; // Preferred!
		int[] arr4 = new int[] { 4, 5, 6 };

		// Multi-dimensional
		int[][] matrix = { { 1, 2 }, { 3, 4 }, { 5, 6 } };

		// Print arrays
		System.out.println("arr2: " + java.util.Arrays.toString(arr2));
		System.out.println("arr3: " + java.util.Arrays.toString(arr3));
		System.out.println("matrix length: " + matrix.length);
	}
}
