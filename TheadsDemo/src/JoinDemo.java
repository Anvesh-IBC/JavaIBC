
public class JoinDemo {
	public static void main(String[] args) throws InterruptedException {
		Thread worker = new Thread(() -> {
			for (int i = 0; i < 3; i++) {
				System.out.println("Worker step " + i);
				try {
					Thread.sleep(300);
				} catch (InterruptedException ignored) {
				}
			}
		});
		worker.start();
		System.out.println("Main waiting for worker...");
		worker.join(); // main blocks
		System.out.println("Worker finished, main continues.");
	}
}

