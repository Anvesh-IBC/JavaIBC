package com.hms.patterns.factory.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.hms.patterns.factory.NotificationFactory;
import com.hms.patterns.factory.Notifier;

class NotificationFactoryTest {

	@Test
	void smsNotifier_works() {
		Notifier n = NotificationFactory.of("SMS");
		assertNotNull(n);
		assertDoesNotThrow(() -> n.notify("+910000000000", "Hello"));
	}

	@Test
	void emailNotifier_works() {
		Notifier n = NotificationFactory.of("EMAIL");
		assertNotNull(n);
		assertDoesNotThrow(() -> n.notify("x@y.com", "Hello"));
	}

	@Test
	void unknownChannel_throws() {
		assertThrows(IllegalArgumentException.class, () -> NotificationFactory.of("PIGEON"));
	}
}
