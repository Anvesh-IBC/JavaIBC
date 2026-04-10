
public class YieldDemo {
	public static void main(String[] args) {
		Runnable r = () -> {
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + " -> " + i);
				Thread.yield(); // hint to scheduler; not guaranteed
			}
		};
		new Thread(r, "Y1").start();
		new Thread(r, "Y2").start();
	}
}
