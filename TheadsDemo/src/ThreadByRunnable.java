
public class ThreadByRunnable {
	static class MyTask implements Runnable {
		@Override
		public void run() {
			System.out.println("Runnable in: " + Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) {
		Runnable task = new MyTask();
		Thread t = new Thread(task, "Runnable-1");
		t.start();
	}
}
