package com.hms.event;

import java.util.concurrent.*;
import java.util.function.Consumer;

public class EventBus {

	private final BlockingQueue<DomainEvent> queue = new LinkedBlockingQueue<>(1000);

	public void publish(DomainEvent event) {
		queue.offer(event); // drops if full (backpressure)
	}

	public void startConsumer(ExecutorService pool, Consumer<DomainEvent> handler) {

		pool.submit(() -> {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					DomainEvent event = queue.take();
					handler.accept(event);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
	}
}
