
public class SleepDemo {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(() -> {
			try {
				for (int i = 1; i <= 3; i++) {
					System.out.println("Tick " + i);
					Thread.sleep(500); // TIMED_WAITING
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t.start();
		t.join(); // wait for t to finish
		System.out.println("Done.");
	}
}
