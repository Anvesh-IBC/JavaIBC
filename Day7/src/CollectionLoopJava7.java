import java.util.ArrayList;
import java.util.List;

public class CollectionLoopJava7 {
	public static void main(String[] args) {
		List<String> cities = new ArrayList<String>();
		cities.add("Pune");
		cities.add("Banglore");
		cities.add("Mumbai");
		cities.add("Chennai");

		for (String city : cities) {
			System.out.println(city);
		}
	}
}
