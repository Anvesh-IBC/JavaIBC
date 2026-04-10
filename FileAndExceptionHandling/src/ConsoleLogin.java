import java.io.Console;

public class ConsoleLogin {
	public static void main(String[] args) {
		Console console = System.console();
		if (console == null) {
			System.out.println("Console not available (likely running inside an IDE). Run from a real terminal.");
			return;
		}
		String user = console.readLine("Username: ");
		char[] pass = console.readPassword("Password: ");
		console.printf("Hello, %s! You entered a password of length %d (not printed).%n", user, pass.length);

		// Always clear sensitive data in memory
		java.util.Arrays.fill(pass, ' ');
	}
}
