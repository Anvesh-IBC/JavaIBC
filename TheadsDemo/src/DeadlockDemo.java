
public class DeadlockDemo {
	private static final Object LOCK_A = new Object();
	private static final Object LOCK_B = new Object();

	public static void main(String[] args) {
		Thread t1 = new Thread(() -> {
			synchronized (LOCK_A) {
				System.out.println("T1 got A, wants B");
				try {
					Thread.sleep(200);
				} catch (InterruptedException ignored) {
				}
				synchronized (LOCK_B) {
					System.out.println("T1 got B");
				}
			}
		}, "T1");

		Thread t2 = new Thread(() -> {
			synchronized (LOCK_B) {
				System.out.println("T2 got B, wants A");
				try {
					Thread.sleep(200);
				} catch (InterruptedException ignored) {
				}
				synchronized (LOCK_A) {
					System.out.println("T2 got A");
				}
			}
		}, "T2");

		t1.start();
		t2.start();
	}
}
