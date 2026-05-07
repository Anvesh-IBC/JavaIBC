
class YieldTask extends Thread {
	public YieldTask(String name, int priority) {
		super(name);
		setPriority(priority);

	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println(getName() + "running with priority" + getPriority() + "count=" + i);
			if (i == 2) {
				System.out.println(getName() + "yielding...");
				Thread.yield();

			}

		}
	}
}
