
public class CalculatorEngine {

	// ARITHMETIC OPERATORS
	public static double arithmeticDemo(double a, double b) {
		System.out.printf("Arithmetic: %.1f %s %.1f = %.1f%n", a, "+", b, a + b);
		System.out.printf("            %.1f %s %.1f = %.1f%n", a, "-", b, a - b);
		System.out.printf("            %.1f %s %.1f = %.1f%n", a, "*", b, a * b);
		System.out.printf("            %.1f %s %.1f = %.1f%n", a, "/", b, a / b);
		System.out.printf("            %.1f %% %.1f = %.1f%n", a, "%", b, a % b);
		return a + b * 2 - b / 2 % 3; // Compound expression
	}

	// ASSIGNMENT OPERATORS
	public static void assignmentDemo() {
		int x = 10;
		System.out.println("x = " + x);

		x += 5; // x = x + 5
		System.out.println("x += 5 -> " + x);

		x *= 2; // x = x * 2
		System.out.println("x *= 2 -> " + x);

		x /= 3; // x = x / 3
		System.out.println("x /= 3 -> " + x);

		x %= 4; // x = x % 4
		System.out.println("x %= 4 -> " + x);
	}

	// RELATIONAL OPERATORS
	public static void relationalDemo(int a, int b) {
		System.out.printf("%d == %d ? %b%n", a, b, a == b);
		System.out.printf("%d != %d ? %b%n", a, b, a != b);
		System.out.printf("%d > %d  ? %b%n", a, b, a > b);
		System.out.printf("%d < %d  ? %b%n", a, b, a < b);
		System.out.printf("%d >= %d ? %b%n", a, b, a >= b);
		System.out.printf("%d <= %d ? %b%n", a, b, a <= b);
	}

	// LOGICAL OPERATORS
	public static void logicalDemo(boolean p, boolean q) {
		System.out.printf("%b && %b = %b%n", p, q, p && q); // AND
		System.out.printf("%b || %b = %b%n", p, q, p || q); // OR
		System.out.printf("!%b     = %b%n", p, !p); // NOT
		System.out.printf("%b ^ %b  = %b%n", p, q, p ^ q); // XOR
	}

	// TERNARY & CONDITIONAL OPERATOR
	public static String gradeCalculator(int marks) {
		// TERNARY OPERATOR
		String grade = marks >= 90 ? "A+" : marks >= 80 ? "A" : marks >= 70 ? "B" : "C";
		return grade;
	}

	// BITWISE OPERATORS
	public static void bitwiseDemo(int a, int b) {
		System.out.printf("%d & %d = %d%n", a, b, a & b); // AND
		System.out.printf("%d | %d = %d%n", a, b, a | b); // OR
		System.out.printf("%d ^ %d = %d%n", a, b, a ^ b); // XOR
		System.out.printf("%d << 2 = %d%n", a, a << 2); // Left shift
		System.out.printf("%d >> 1 = %d%n", a, a >> 1); // Right shift
	}
}
