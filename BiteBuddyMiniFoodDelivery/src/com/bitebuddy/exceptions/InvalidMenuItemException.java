package com.bitebuddy.exceptions;

public class InvalidMenuItemException extends RuntimeException {
	public InvalidMenuItemException(int id) {
		super("Invalid menu item: " + id);
	}
}
