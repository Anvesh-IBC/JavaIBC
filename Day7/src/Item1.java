
public class Item1 {
	private String name = "Unknown";
	private double price = 0.0;

	public Item1() {
	}

	public Item1(String name, double price) {
		this.name = name;
		this.price = price;

	}

	//getters/setters...

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ",price=" + price + "]";
	}
}
