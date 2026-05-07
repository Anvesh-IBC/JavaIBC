
class StateThread extends Thread {
	@Override
	public void run() {
		try {
			System.out.println("Inside run(), state: " + Thread.currentThread().getState());
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
