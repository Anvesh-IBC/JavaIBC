
public class NotifyAllDemo {
	public static void main(String[] args) throws Exception {
		SharedResource resource = new SharedResource();

		Thread t1 = new Thread(() -> resource.waitForResource("Thread-1"));
		Thread t2 = new Thread(() -> resource.waitForResource("Thread-2"));
		Thread t3 = new Thread(() -> resource.waitForResource("Thread-3"));

		t1.start();
		t2.start();
		t3.start();

		Thread.sleep(2000);

		Thread producer = new Thread(resource::makeAvailable);
		producer.start();
	}
}
