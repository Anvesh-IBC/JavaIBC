import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LambdaBasicsDemo {

	// A custom functional interface (for clarity)
	@FunctionalInterface
	interface Greeting {
		void sayHello(String name);
	}

	public static void main(String[] args) {

		System.out.println("=== Runnable: Old vs Lambda ===");
		// Old Style (Pre-Java 8)
		Runnable oldStyle = new Runnable() {
			@Override
			public void run() {
				System.out.println("Running... (old style)");
			}
		};
		oldStyle.run();

		// Lambda Style
		Runnable lambdaStyle = () -> System.out.println("Running... (lambda style)");
		lambdaStyle.run();

		System.out.println("\n=== Lambda Syntax Examples ===");

		// 1) Lambda with No Parameters
		// Equivalent to: () -> { System.out.println("Hello Lambda"); }
		Supplier<String> noParam = () -> "Hello Lambda";
		System.out.println(noParam.get());

		// 2) Lambda with One Parameter
		// Parentheses can be omitted because there is exactly one parameter
		Consumer<String> oneParam = name -> System.out.println("Hello, " + name);
		oneParam.accept("Naveen");

		// 3) Lambda with Multiple Parameters
		// Parentheses required for more than one parameter
		BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
		System.out.println("Sum (10, 20) = " + add.apply(10, 20));

		System.out.println("\n=== Block Lambda with Return Value ===");
		// Multi-line body with explicit return
		BiFunction<Integer, Integer, Integer> max = (x, y) -> {
			if (x >= y)
				return x;
			return y;
		};
		System.out.println("Max(7, 9) = " + max.apply(7, 9));

		System.out.println("\n=== Custom Functional Interface + Lambda ===");
		Greeting g = nm -> System.out.println("Hello " + nm + "! Welcome to Java 8 Lambdas.");
		g.sayHello("Naveen");

		System.out.println("\n=== Lambdas with Threads ===");
		Thread t = new Thread(() -> System.out.println("Thread running via lambda..."));
		t.start();
		try {
			t.join();
		} catch (InterruptedException ignored) {
		}

		System.out.println("\n=== Extra: Method Reference (bonus) ===");
		// Method references are shorthand for common lambda patterns
		Consumer<String> printer = System.out::println; // s -> System.out.println(s)
		printer.accept("Printed using method reference!");
	}
}