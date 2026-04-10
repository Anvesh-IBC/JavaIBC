package PassByValue.PrimitiveVsObject;

public class PassByValueDemo {
	static void modify(int value) {
		value = 100;
		System.out.println("Inside method: " + value);
	}

	public static void main(String[] args) {
		int number = 50;
		modify(number); // Just a copy is sent
		System.out.println("After method: " + number); // original remains 50
	}
}
