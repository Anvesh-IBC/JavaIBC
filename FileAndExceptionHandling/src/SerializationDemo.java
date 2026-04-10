import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationDemo {
	public static void main(String[] args) {
		File file = new File("demo-data/orders.ser");
		file.getParentFile().mkdirs();

		List<Order> orders = new ArrayList<>();
//		Order o = new Order("O-101", "Pizza", 2, "VIP customer");
		orders.add(new Order("O-101", "Pizza", 2, "VIP customer"));
		orders.add(new Order("O-102", "Burger", 1, "Deliver by 7 PM"));

		// Serialize
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(orders);
			System.out.println("Serialized " + orders.size() + " orders.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Deserialize
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			List<?> restored = (List<?>) ois.readObject();
			System.out.println("Deserialized: " + restored);
			// Notice internalNote becomes null because it was transient
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

