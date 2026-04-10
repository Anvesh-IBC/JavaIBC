
public class ThreadStatesDemo {
	private static final Object LOCK = new Object();

	public static void main(String[] args) throws Exception {
		Thread sleeper = new Thread(() -> {
			try {
				System.out.println("Sleeper: Sleeping...");
				Thread.sleep(1500);
				synchronized (LOCK) {
					System.out.println("Sleeper: Waiting...");
					LOCK.wait(); // goes to WAITING
					System.out.println("Sleeper: Notified and finishing");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "Sleeper");

		System.out.println("State before start: " + sleeper.getState()); // NEW
		sleeper.start();
		Thread.sleep(100); // let it start
		System.out.println("After start: " + sleeper.getState()); // RUNNABLE or TIMED_WAITING

		Thread.sleep(800);
		System.out.println("Midway: " + sleeper.getState()); // likely TIMED_WAITING (sleep)

		Thread.sleep(1000);
		System.out.println("After sleep done: " + sleeper.getState()); // WAITING (after wait())

		synchronized (LOCK) {
			LOCK.notifyAll();
		}

		Thread.sleep(100);
		System.out.println("Final: " + sleeper.getState()); // RUNNABLE or TERMINATED
	}
}
