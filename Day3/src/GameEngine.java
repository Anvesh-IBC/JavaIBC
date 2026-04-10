
import java.util.Random;
import java.util.Scanner;

public class GameEngine {

	private static Scanner scanner = new Scanner(System.in);
	private static Random random = new Random();

	// ---------- IF-ELSE CHAINING ----------
	public static void bmiCalculator() {
		System.out.print("Enter weight (kg): ");
		double weight = scanner.nextDouble();
		System.out.print("Enter height (m): ");
		double height = scanner.nextDouble();

		double bmi = weight / (height * height);
		System.out.printf("BMI: %.1f | ", bmi);

		if (bmi < 18.5) {
			System.out.println("Underweight");
		} else if (bmi < 25) {
			System.out.println("Normal");
		} else if (bmi < 30) {
			System.out.println("Overweight");
		} else {
			System.out.println("Obese");
		}
	}

	// ---------- SWITCH STATEMENT (Java 7 syntax) ----------
	public static void menuDrivenCalculator() {
		while (true) {
			System.out.print("\n=== Calculator ===\n1.Add 2.Sub 3.Mul 4.Div 0.Exit\nChoice: ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				System.out.print("a: ");
				double a1 = scanner.nextDouble();
				System.out.print("b: ");
				double b1 = scanner.nextDouble();
				System.out.printf("%.1f + %.1f = %.1f%n", a1, b1, a1 + b1);
				break;
			case 2:
				System.out.print("a: ");
				double a2 = scanner.nextDouble();
				System.out.print("b: ");
				double b2 = scanner.nextDouble();
				System.out.printf("%.1f - %.1f = %.1f%n", a2, b2, a2 - b2);
				break;
			case 3:
				System.out.print("a: ");
				double a3 = scanner.nextDouble();
				System.out.print("b: ");
				double b3 = scanner.nextDouble();
				System.out.printf("%.1f * %.1f = %.1f%n", a3, b3, a3 * b3);
				break;
			case 4:
				System.out.print("a: ");
				double a4 = scanner.nextDouble();
				System.out.print("b: ");
				double b4 = scanner.nextDouble();
				if (b4 == 0) {
					System.out.println("Cannot divide by zero!");
				} else {
					System.out.printf("%.1f / %.1f = %.1f%n", a4, b4, a4 / b4);
				}
				break;
			case 0:
				System.out.println("Bye!");
				return;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	// ---------- LOOP DEMONSTRATION ----------
	public static void loopDemo() {
		int[] numbers = { 10, 20, 30, 40, 50 };

		System.out.print("FOR: ");
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + " ");
		}
		System.out.println();

		System.out.print("ENHANCED FOR: ");
		for (int num : numbers) {
			System.out.print(num + " ");
		}
		System.out.println();

		System.out.print("WHILE: ");
		int i = 0;
		while (i < numbers.length) {
			System.out.print(numbers[i] + " ");
			i++;
		}
		System.out.println();

		System.out.print("DO-WHILE: ");
		int j = 0;
		do {
			System.out.print(numbers[j] + " ");
			j++;
		} while (j < numbers.length);
		System.out.println();
	}

	// ---------- BREAK & CONTINUE ----------
	public static void breakContinueDemo() {
		System.out.println("\nNumbers 1-10 (skip multiples of 3, stop at 8):");
		for (int i = 1; i <= 10; i++) {
			if (i % 3 == 0) {
				continue;
			}
			if (i == 8) {
				break;
			}
			System.out.print(i + " ");
		}
		System.out.println();
	}

	// ---------- NUMBER-GUESSING GAME ----------
	public static void numberGuessingGame() {
		int secret = random.nextInt(100) + 1;
		int attempts = 0;
		int guess = 0;

		System.out.println("\nGuess the number (1-100):");
		do {
			System.out.print("Guess: ");
			guess = scanner.nextInt();
			attempts++;

			if (guess < secret) {
				System.out.println("Too low!");
			} else if (guess > secret) {
				System.out.println("Too high!");
			} else {
				System.out.printf("Correct! You took %d attempts.%n", attempts);
				break;
			}
		} while (true);
	}

	public static void main(String[] args) {
		// bmiCalculator();
		// menuDrivenCalculator();
		// loopDemo();
		// breakContinueDemo();
		// numberGuessingGame();
	}
}
