package com.hms.patterns.factory;

public class NotificationFactory {
	public static Notifier of(String channel) {
		if ("SMS".equalsIgnoreCase(channel))
			return new SmsNotifier();
		if ("EMAIL".equalsIgnoreCase(channel))
			return new EmailNotifier();
		throw new IllegalArgumentException("Unknown channel:" + channel);
	}
}
