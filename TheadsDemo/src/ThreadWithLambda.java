
public class ThreadWithLambda {
	public static void main(String[] args) {
		Thread t = new Thread(() -> System.out.println("Lambda run on: " + Thread.currentThread().getName()),
				"Lambda-Thread");
		t.start();
	}
}
