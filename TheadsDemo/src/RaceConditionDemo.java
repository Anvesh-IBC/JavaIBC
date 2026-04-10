
public class RaceConditionDemo {
	static class Counter {
		int count = 0;

		void increment() { // NOT synchronized
			count++; // not atomic: read-modify-write
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Counter c = new Counter();
		Runnable r = () -> {
			for (int i = 0; i < 100_000; i++)
				c.increment();
		};
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println("Expected: 200000, Actual: " + c.count);
	}
}
