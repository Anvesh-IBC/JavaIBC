
import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerDemo {
	static class BoundedBuffer<T> {
		private final Queue<T> q = new LinkedList<>();
		private final int capacity;

		BoundedBuffer(int capacity) {
			this.capacity = capacity;
		}

		public void put(T item) throws InterruptedException {
			synchronized (this) {
				while (q.size() == capacity) {
					wait(); // releases lock, waits for space
				}
				q.add(item);
				notifyAll(); // wake up waiting consumers
			}
		}

		public T take() throws InterruptedException {
			synchronized (this) {
				while (q.isEmpty()) {
					wait(); // releases lock, waits for items
				}
				T item = q.remove();
				notifyAll(); // wake up producers waiting for space
				return item;
			}
		}
	}

	public static void main(String[] args) {
		BoundedBuffer<Integer> buffer = new BoundedBuffer<>(2);

		Runnable producer = () -> {
			for (int i = 1; i <= 5; i++) {
				try {
					buffer.put(i);
					System.out.println(Thread.currentThread().getName() + " produced " + i);
					Thread.sleep(100);
				} catch (InterruptedException ignored) {
				}
			}
		};

		Runnable consumer = () -> {
			for (int i = 1; i <= 5; i++) {
				try {
					Integer v = buffer.take();
					System.out.println(Thread.currentThread().getName() + " consumed " + v);
					Thread.sleep(150);
				} catch (InterruptedException ignored) {
				}
			}
		};

		new Thread(producer, "P1").start();
		new Thread(producer, "P2").start();
		new Thread(consumer, "C1").start();
		new Thread(consumer, "C2").start();
	}
}
