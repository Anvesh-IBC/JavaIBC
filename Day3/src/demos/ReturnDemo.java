package demos;

public class ReturnDemo {

	int getNumber() {
		return 10;
	}

	String getMessage() {
		return "Hello";
	}

	public static void main(String[] args) {

		ReturnDemo r = new ReturnDemo();
		System.out.println(r.getNumber());
		System.out.println(r.getMessage());
	}
}
