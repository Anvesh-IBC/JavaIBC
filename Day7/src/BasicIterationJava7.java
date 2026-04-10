import java.util.Arrays;
import java.util.List;

public class BasicIterationJava7 {
	public static void main(String[] args) {
		String[] arr = { "I", "G", "A", "T", "E", "\n" };
		for (String s : arr) {
			System.out.print(s);
		}

		List<String> locations = Arrays.asList("Pune", "Mumbai", "Chennai", "Banglore", "Noida");
		for (String city : locations) {
			System.out.println(city);
		}
	}
}
