import java.util.Arrays;
import java.util.List;

public class Mapping1 {
	public static void main(String[] args) {
		List<String> locations = Arrays.asList("Pune", "Mumbai", "Chennai", "Banglore", "Noida");
		System.out.println("Word length for locations:");
		locations.stream().map(String::length).forEach(len -> System.out.print(len + " "));
	}
}
