
public class LockDemo {
	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread t1 = new Thread(() -> printer.printTable(5), "T1");
		Thread t2 = new Thread(() -> printer.printTable(10), "T2");

		t1.start();
		t2.start();

	}
}
