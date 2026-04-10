
public class CalculatorEngineDemo {
	public static void main(String[] args) {
		System.out.println(" Java Operators - Complete Coverage Demo");
		System.out.println("===========================\n");

		CalculatorEngine engine = new CalculatorEngine();

		// 1. ARITHMETIC OPERATORS
		System.out.println(" ARITHMETIC OPERATORS:");
		double result = engine.arithmeticDemo(10.5, 3.2);
		System.out.printf("Compound: 10.5 + 3.2 * 2 - 3.2 / 2 %% 3 = %.1f%n%n", result);

		// 2. ASSIGNMENT OPERATORS
		System.out.println(" ASSIGNMENT OPERATORS:");
		engine.assignmentDemo();
		System.out.println();

		// 3. RELATIONAL OPERATORS
		System.out.println(" RELATIONAL OPERATORS:");
		engine.relationalDemo(15, 10);
		System.out.println();

		// 4. LOGICAL OPERATORS
		System.out.println(" LOGICAL OPERATORS:");
		engine.logicalDemo(true, false);
		System.out.println();

		// 5. TERNARY OPERATOR
		System.out.println(" TERNARY OPERATOR:");
		System.out.println("85 marks -> " + engine.gradeCalculator(85));
		System.out.println("92 marks -> " + engine.gradeCalculator(92));
		System.out.println("65 marks -> " + engine.gradeCalculator(65));
		System.out.println();

		// 6. BITWISE OPERATORS
		System.out.println(" BITWISE OPERATORS:");
		engine.bitwiseDemo(12, 5); // 12=1100, 5=0101
		System.out.println();

		// 7. UNARY OPERATORS (BONUS)
		System.out.println(" UNARY OPERATORS:");
		unaryDemo();

		System.out.println("\n Complete Operators Coverage - 100% OCP Ready!");
	}

	// BONUS: Unary Operators Demo
	public static void unaryDemo() {
		int x = 5;
		System.out.println("x = " + x);

		System.out.println("Post-increment: " + (x++) + " (x now: " + x + ")");
		System.out.println("Pre-increment:  " + (++x) + " (x now: " + x + ")");
		System.out.println("Post-decrement: " + (x--) + " (x now: " + x + ")");
		System.out.println("Pre-decrement:  " + (--x) + " (x now: " + x + ")");

		boolean flag = true;
		System.out.println("!true = " + !flag);

		System.out.println("(int)3.99 = " + (int) 3.99); // Cast operator
		System.out.println();
	}
}
