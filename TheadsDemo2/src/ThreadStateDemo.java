
class ThreadStateDemo {
	public static void main(String[] args) throws Exception {
		StateThread t = new StateThread();
		System.out.println("After creation:" + t.getState()); // NEW
		t.start();
		System.out.println("After start:" + t.getState()); // RUNNABLE

		Thread.sleep(200);
		System.out.println("While sleeping:" + t.getState()); // TIMED_WAITING likely

		t.join();
		System.out.println("After completion:" + t.getState()); // TERMINATED

	}
}
