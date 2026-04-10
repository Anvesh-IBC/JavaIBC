package ArraysInitializationBlocks;

public class ArrayFullDemo {
	public static void main(String[] args) {
		int[] ages = new int[3]; // declare and construct
		ages[0] = 22; // initialize elements
		ages[1] = 25;
		ages[2] = 27;

		for (int i = 0; i < ages.length; i++) {
			System.out.println("Friend " + (i + 1) + " age: " + ages[i]);
		}
	}
}
