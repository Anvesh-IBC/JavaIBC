class SharedResource {
	private boolean available = false;

	public synchronized void waitForResource(String threadName) {
		while (!available) {
			try {
				System.out.println(threadName + " is waiting...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(threadName + " got the resource.");
	}

	public synchronized void makeAvailable() {
		available = true;
		System.out.println("Resource is now available. Notifying all...");
		notifyAll();
	}
}