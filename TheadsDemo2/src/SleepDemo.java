
public class SleepDemo {
	public static void main(String[] args) {
		Thread t = new Thread(() -> {
			try {
				System.out.println("Thread sleeping for 2 seconds...");
				Thread.sleep(2000);
				System.out.println("Thread woke up.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t.start();
	}
}
