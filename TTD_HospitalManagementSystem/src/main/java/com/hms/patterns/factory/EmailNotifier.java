package com.hms.patterns.factory;

public class EmailNotifier implements Notifier {
	@Override
	public void notify(String to, String msg) {
		System.out.println("[EMAIL] to=" + to + " msg=" + msg);
	}
}
