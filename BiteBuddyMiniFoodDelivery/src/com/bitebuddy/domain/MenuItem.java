package com.bitebuddy.domain;

import java.math.BigDecimal;

public class MenuItem {
	private int id;
	private String name;
	private BigDecimal price;
	private boolean veg;
	private Category category;

	public MenuItem() {
	}

	public MenuItem(int id, String name, BigDecimal price, boolean veg, Category category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.veg = veg;
		this.category = category;
	}

	public MenuItem(int id, String name) {
		this(id, name, BigDecimal.ZERO, true, null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isVeg() {
		return veg;
	}

	public void setVeg(boolean veg) {
		this.veg = veg;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return id + " " + name + " (" + (veg ? "Veg" : "Non-veg") + ") " + price;
	}
}
