package com.hms.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.*;

import com.hms.domain.Appointment;

public class CheckInQueue {
	private final BlockingQueue<Appointment> queue = new LinkedBlockingQueue<>();

	public void submit(Appointment a) {
		try {
			queue.put(a);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public Appointment take() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}
}
