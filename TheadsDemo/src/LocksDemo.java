
public class LocksDemo {
	static class Service {
		// Instance lock (this)
		public synchronized void actionA() {
			System.out.println(Thread.currentThread().getName() + " in actionA");
			actionB(); // Reentrant: can re-lock the same monitor
		}

		public synchronized void actionB() {
			System.out.println(Thread.currentThread().getName() + " in actionB");
		}

		private static final Object CLASS_LOCK = new Object();

		// Class-level lock via explicit object
		public void doClassWork() {
			synchronized (CLASS_LOCK) {
				System.out.println(Thread.currentThread().getName() + " doing class-level work");
				try {
					Thread.sleep(300);
				} catch (InterruptedException ignored) {
				}
			}
		}
	}

	public static void main(String[] args) {
		Service s1 = new Service();
		Service s2 = new Service();

		// Shows instance-level locking per object
		new Thread(s1::actionA, "T1").start();
		new Thread(s2::actionA, "T2").start(); // does not block T1 (different instance)

		// Shows class-level lock blocks across instances
		new Thread(s1::doClassWork, "C1").start();
		new Thread(s2::doClassWork, "C2").start();
	}
}
