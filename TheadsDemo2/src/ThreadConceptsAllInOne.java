public class ThreadConceptsAllInOne {
	public static void main(String[] args) throws Exception {

		Thread thread1 = new Thread(() -> {
			for (int i = 1; i <= 3; i++) {
				System.out.println(Thread.currentThread().getName() + " running " + i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Thread.yield();
			}
		}, "Worker-1");

		Thread thread2 = new Thread(() -> {
			for (int i = 1; i <= 3; i++) {
				System.out.println(Thread.currentThread().getName() + " running " + i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "Worker-2");

		SharedCounter counter = new SharedCounter();

		Thread inc1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++)
				counter.increment();
		});

		Thread inc2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++)
				counter.increment();
		});

		System.out.println("State before start: " + thread1.getState());

		thread1.start();
		thread2.start();
		inc1.start();
		inc2.start();

		thread1.join();
		thread2.join();
		inc1.join();
		inc2.join();

		System.out.println("State after finish: " + thread1.getState());
		System.out.println("Final synchronized count:" + counter.getCount());
	}
}