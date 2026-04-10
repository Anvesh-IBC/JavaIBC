package DefaultValuesInitializationRules;

public class InstanceDemo {
	int x; // 0 (default)
	boolean b; // false
	String s; // null

	void printDefaults() {
		System.out.println("x=" + x + ", b=" + b + ", s=" + s);
	}

	public static void main(String[] args) {
		new InstanceDemo().printDefaults();
		// Output: x=0, b=false, s=null
	}
}
