import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filtering {
	public static void main(String[] args) {
		List<String> locations = Arrays.asList("Pune", "Mumbai", "Chennai", "Banglore", "Noida");
		List<String> result = locations.stream().filter(location -> location.length() > 5).collect(Collectors.toList());
		result.forEach(city -> System.out.println(city));
	}
}
