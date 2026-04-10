package com.hms.service;

public class Mailer {

	public void send(String to, String subject, String body, String attachmentPath) {

		// Simulate sending email (async-friendly)
		System.out.println("Email sent to " + to + " | subject=" + subject + " | attachment=" + attachmentPath);
	}
}
