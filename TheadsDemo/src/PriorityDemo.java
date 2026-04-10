
public class PriorityDemo {
	static class Busy implements Runnable {
		private final long loops;

		Busy(long loops) {
			this.loops = loops;
		}

		@Override
		public void run() {
			long s = System.currentTimeMillis();
			long sum = 0;
			for (long i = 0; i < loops; i++)
				sum += i;
			long e = System.currentTimeMillis();
			System.out.println(Thread.currentThread().getName() + " finished in " + (e - s) + " ms, sum=" + sum);
		}
	}

	public static void main(String[] args) {
		Thread low = new Thread(new Busy(80_000_000L), "LOW");
		Thread high = new Thread(new Busy(80_000_000L), "HIGH");

		low.setPriority(Thread.MIN_PRIORITY); // 1
		high.setPriority(Thread.MAX_PRIORITY); // 10

		low.start();
		high.start();
	}
}
