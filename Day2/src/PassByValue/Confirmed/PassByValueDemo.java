package PassByValue.Confirmed;

public class PassByValueDemo {
	public static void main(String[] args) {
// PRIMITIVES: copy of VALUE passed
		int x = 10;
		System.out.println("Before: x=" + x); // 10
		modifyPrimitive(x);
		System.out.println("After: x=" + x); // 10 (unchanged!)

// OBJECTS: copy of REFERENCE passed
		StringBuilder sb = new StringBuilder("Hello");
		System.out.println("Before: " + sb); // Hello
		modifyObject(sb);
		System.out.println("After: " + sb); // Hello World (modified!)
	}

	static void modifyPrimitive(int y) {
		y = 100; // Changes local copy only
	}

	static void modifyObject(StringBuilder temp) {
		temp.append(" World"); // Modifies SAME heap object
	}
}
