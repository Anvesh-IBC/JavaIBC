package Arrays.AllForms;

public class ArrayEdgeCases {
	public static void main(String[] args) {
// Anonymous arrays
		int[] anon = new int[] { 1, 2, 3 };

// Array of objects (null by default)
		String[] strings = new String[3]; // [null, null, null]

// Variable length initialization
		for (int i = 0; i < strings.length; i++) {
			strings[i] = "str" + i;
		}

// Jagged arrays
		int[][] jagged = new int[3][];
		jagged[0] = new int[2];
		jagged[1] = new int[4];
		jagged[2] = new int[1];

		System.out.println(java.util.Arrays.deepToString(jagged));
	}
}
