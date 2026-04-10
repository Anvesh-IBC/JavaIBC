
import java.util.LinkedList;
import java.util.Queue;

public class NotifyVsNotifyAllDemo {
	static class Buffer {
		private final Queue<Integer> q = new LinkedList<>();
		private final int cap = 1;

		public void put(int v) throws InterruptedException {
			synchronized (this) {
				while (q.size() == cap) {
					wait();
				}
				q.add(v);
				// notify(); // risk: wakes a producer when full -> no progress
				notifyAll(); // safer when many waiters
			}
		}

		public int take() throws InterruptedException {
			synchronized (this) {
				while (q.isEmpty()) {
					wait();
				}
				int v = q.remove();
				// notify(); // same risk (wakes consumer when empty)
				notifyAll(); // safer
				return v;
			}
		}
	}

	public static void main(String[] args) {
		Buffer b = new Buffer();
		Runnable producer = () -> {
			for (int i = 1; i <= 5; i++) {
				try {
					b.put(i);
					System.out.println(Thread.currentThread().getName() + " put " + i);
				} catch (InterruptedException ignored) {
				}
			}
		};
		Runnable consumer = () -> {
			for (int i = 1; i <= 5; i++) {
				try {
					int v = b.take();
					System.out.println(Thread.currentThread().getName() + " took " + v);
				} catch (InterruptedException ignored) {
				}
			}
		};

		// Start many waiters
		new Thread(producer, "P1").start();
		new Thread(producer, "P2").start();
		new Thread(consumer, "C1").start();
		new Thread(consumer, "C2").start();
	}
}
