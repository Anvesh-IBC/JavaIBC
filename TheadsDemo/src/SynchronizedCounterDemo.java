
public class SynchronizedCounterDemo {
	static class Counter {
		private int count = 0;

		synchronized void increment() { // acquire intrinsic lock
			count++;
		}

		synchronized int value() {
			return count;
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
		System.out.println("Expected: 200000, Actual: " + c.value());
	}
}
