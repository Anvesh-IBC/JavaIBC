package com.hms.patterns.factory;

public class SmsNotifier implements Notifier {
	@Override
	public void notify(String to, String msg) {
		System.out.println("[SMS] to" + to + "msg=" + msg);
	}
}
