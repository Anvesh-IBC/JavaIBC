package LocalVariableInitializationRules;

public class UninitializedDemo {
	public static void main(String[] args) {
		int x; // ERROR: local variable not initialized
		// System.out.println(x); // Compile error!

		int y = 10;
		if (y > 5) {
			x = 20; // OK within scope
		}
		// System.out.println(x); // ERROR: may not be initialized

		int z = 0;
		if (true)
			z = 1; // Definite path -> OK
		System.out.println(z); // Works!
	}
}
