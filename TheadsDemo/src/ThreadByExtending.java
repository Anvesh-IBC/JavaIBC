
public class ThreadByExtending extends Thread {
	@Override
	public void run() {
		System.out.println("Running in: " + Thread.currentThread().getName());
	}

	public static void main(String[] args) {
		ThreadByExtending t1 = new ThreadByExtending(); // instantiate
		t1.setName("Extender-1");
		t1.start(); // start => new OS thread + calls run()
		System.out.println("Main is: " + Thread.currentThread().getName());
	}
}
