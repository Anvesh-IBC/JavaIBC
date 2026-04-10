
public class DeadlockPreventionDemo {
	private static final Object A = new Object();
	private static final Object B = new Object();

	public static void main(String[] args) {
		Runnable r = () -> {
			// Global order: always lock A, then B
			synchronized (A) {
				System.out.println(Thread.currentThread().getName() + " got A");
				try {
					Thread.sleep(100);
				} catch (InterruptedException ignored) {
				}
				synchronized (B) {
					System.out.println(Thread.currentThread().getName() + " got B");
				}
			}
		};
		new Thread(r, "P1").start();
		new Thread(r, "P2").start();
	}
}
