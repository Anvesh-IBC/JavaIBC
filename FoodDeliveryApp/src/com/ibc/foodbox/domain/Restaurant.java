package com.ibc.foodbox.domain;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
	private int id;
	private String name;
	private List<MenuItem> menu;

	public Restaurant() {
		this.menu = new ArrayList<MenuItem>();
	}

	public Restaurant(int id, String name) {
		this();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<MenuItem> getMenu() {
		return menu;
	}

	public void addMenuItem(MenuItem mi) {
		if (mi != null)
			this.menu.add(mi);
	}

	@Override
	public String toString() {
		return "Restaurant {id=" + id + ", name=" + name + ", menuSize=" + menu.size() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Restaurant))
			return false;
		return id == ((Restaurant) o).id;
	}

	@Override
	public int hashCode() {
		return id;
	}
}
