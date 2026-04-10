
public class ConstructorReferenceJava7_Simple {

	public static void main(String[] args) {
		Item1 item = new Item1(); // direct construction(no Supplier, no method reference)
		System.out.println(item.getName());
		System.out.println(item.getPrice());

	}

}
