import java.util.Arrays;
import java.util.List;

public class ReduceDemoJava7 {
	public static void main(String[] args) {
		List<Integer> intList = Arrays.asList(5, 7, 13, 9, -1);
		Integer max = null;
		for (Integer n : intList) {
			if (n > 0) {
				if (max == null || n > max) {
					max = n;
				}
			}
		}
		if (max != null) {
			System.out.println("Result: " + max);
		} else {
			System.out.println("No positive numbers.");
		}
	}
}
