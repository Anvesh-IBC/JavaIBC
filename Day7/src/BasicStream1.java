import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BasicStream1 {
	public static void main(String[] args) {

		// stream can be created with static data
		Stream<String> stream = Stream.of("I", "G", "A", "T", "E", "\n");
		stream.forEach(location -> System.out.print(location)); // prints IGATE\n

		// stream can be acquired from array or collections
		List<String> locations = Arrays.asList("Pune", "Mumbai", "Chennai", "Banglore", "Noida");
		locations.stream().forEach(System.out::println);
	}
}
