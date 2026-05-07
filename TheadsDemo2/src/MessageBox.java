
public class MessageBox {
	private String message;
	private boolean available = false;

	public synchronized void produce(String msg) {
		while (available) {
			try {
				wait();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		message = msg;
		available = true;
		System.out.println("Produced:" + msg);
		notify();

	}

	public synchronized void consume() {
		while (!available) {
			try {
				wait();

			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
		System.out.println("consumed:" + message);
		available = false;
		notify();

	}

}
