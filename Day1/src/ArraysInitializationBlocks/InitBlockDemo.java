package ArraysInitializationBlocks;

public class InitBlockDemo {
	int x;

	// Instance init block (runs every new object)
	{
		x = 99;
		System.out.println("Init block, x set to " + x);
	}

	public InitBlockDemo() {
		System.out.println("Constructor, x is " + x);
	}

	public static void main(String[] args) {
		InitBlockDemo obj = new InitBlockDemo();
	}
}
