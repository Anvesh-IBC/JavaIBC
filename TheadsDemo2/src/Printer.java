class Printer {
	public void printTable(int n) {
		synchronized (this) {
			for (int i = 1; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + "->" + (n * i));
				try {
					Thread.sleep(300);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}
}
