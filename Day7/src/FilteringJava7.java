import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringJava7 {
	public static void main(String[] args) {
		List<String> locations = Arrays.asList("Pune", "Mumbai", "Chennai", "Banglore", "Noida");
		List<String> result = new ArrayList<String>();
		for (String city : locations) {
			if (city.length() > 5) {
				result.add(city);
			}
		}
		for (String city : result) {
			System.out.println(city);
		}
	}
}
