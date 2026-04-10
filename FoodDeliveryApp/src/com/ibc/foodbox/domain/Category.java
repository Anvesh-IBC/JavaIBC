package com.ibc.foodbox.domain;

import java.util.HashSet;
import java.util.Set;

public class Category {
	private int id;
	private String name;
	private Set<MenuItem> items;

	public Category() {
		this.items = new HashSet<MenuItem>();
	}

	public Category(int id, String name) {
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

	public Set<MenuItem> getItems() {
		return items;
	}

	public void addItem(MenuItem mi) {
		if (mi != null)
			this.items.add(mi);
	}

	@Override
	public String toString() {
		return "Category {id=" + id + ", name=" + name + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Category))
			return false;
		return id == ((Category) o).id;
	}

	@Override
	public int hashCode() {
		return id;
	}

}
