import java.util.Arrays;
import java.util.List;

public class MappingJava7 {
	public static void main(String[] args) {
		List<String> locations = Arrays.asList("Pune", "Mumbai", "Chennai", "Banglore", "Noida");
		System.out.println("Word length for locations:");
		for (String city : locations) {
			int len = city.length();
			System.out.print(len + " ");
		}
	}
}
