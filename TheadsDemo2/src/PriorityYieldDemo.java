
public class PriorityYieldDemo {
	public static void main(String[] args) {
		YieldTask t1 = new YieldTask("LowPriorityThread", Thread.MIN_PRIORITY);
		YieldTask t2 = new YieldTask("HighPriorityThread", Thread.MAX_PRIORITY);

		t1.start();
		t2.start();
	}
}
