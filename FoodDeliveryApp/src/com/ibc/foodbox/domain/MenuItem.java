package com.ibc.foodbox.domain;

import java.util.HashSet;
import java.util.Set;

public class MenuItem {
	private int id;
	private String name;
	private double price;
	private Set<Category> categories;

	public MenuItem() {
		this.categories = new HashSet<Category>();
	}

	public MenuItem(int id, String name, double price) {
		this();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void addCategory(Category c) {
		if (c != null) {
			this.categories.add(c);
			c.addItem(this); // maintain other side (Category.addItem doesn't call back)
		}
	}

	@Override
	public String toString() {
		return "MenuItem{id=" + id + ", name='" + name + "', price=" + price + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof MenuItem))
			return false;
		return id == ((MenuItem) o).id;
	}

	@Override
	public int hashCode() {
		return id;
	}
}