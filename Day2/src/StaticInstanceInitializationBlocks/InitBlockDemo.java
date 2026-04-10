package StaticInstanceInitializationBlocks;

class InitBlockDemo {
	// Static initialization (runs once at class load)
	static {
		System.out.println("Static block executed");
	}

	// Instance initialization (runs per object creation)
	{
		System.out.println("Instance block executed");
	}

	static int staticVar = initStatic();
	int instanceVar = initInstance();

	static int initStatic() {
		System.out.println("Static init method");
		return 10;
	}

	int initInstance() {
		System.out.println("Instance init method");
		return 20;
	}

	public static void main(String[] args) {
		System.out.println("Creating obj1...");
		InitBlockDemo obj1 = new InitBlockDemo();

		System.out.println("\nCreating obj2...");
		InitBlockDemo obj2 = new InitBlockDemo();
	}
}
