package com.ecommerce.service;

import com.ecommerce.observer.Observer;

public interface Observable {
	void addObserver(Observer o);
}
