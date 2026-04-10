package com.bitebuddy.domain;

public class Rider extends User {
	private int currentLoad;
	private String serviceArea;

	public Rider() {
	}

	public Rider(long id, String name, int currentLoad, String serviceArea) {
		super(id, name);
		this.currentLoad = currentLoad;
		this.serviceArea = serviceArea;
	}

	public int getCurrentLoad() {
		return currentLoad;
	}

	public void setCurrentLoad(int currentLoad) {
		this.currentLoad = currentLoad;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
}