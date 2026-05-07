
public class WaitNotifyDemo {
	public static void main(String[] args) {
		MessageBox box = new MessageBox();
		Thread producer = new Thread(() -> {
			for (int i = 1; i <= 5; i++) {
				box.produce("message-" + i);

			}
		});
		Thread consumer = new Thread(() -> {
			for (int i = 1; i <= 5; i++) {
				box.consume();
			}
		});
		producer.start();
		consumer.start();
	}
}
