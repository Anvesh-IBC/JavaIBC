package com.ibc.training.shop;

import java.math.BigDecimal;

public abstract class Product {
	private final int productId;
	private final String name;
	private final BigDecimal unitPrice;
	private final int stock;

	private Category[] categories = new Category[2];
	private int categoryCount = 0;

	public Product(int productId, String name, BigDecimal unitPrice, int stock) {
		if (name == null)
			throw new IllegalArgumentException("name required");
		if (unitPrice == null)
			throw new IllegalArgumentException("unitPrice required");
		if (stock < 0)
			throw new IllegalArgumentException("stock cannot be negative");
		this.productId = productId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.stock = stock;
	}

	public int getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public int getStock() {
		return stock;
	}

	public int getCategoryCount() {
		return categoryCount;
	}

	public Category[] getCategoriesSnapshot() {
		Category[] copy = new Category[categoryCount];
		for (int i = 0; i < categoryCount; i++)
			copy[i] = categories[i];
		return copy;
	}

	public void addCategory(Category c) {
		if (c == null)
			return;
		if (indexOfCategory(c) >= 0)
			return;
		ensureCategoryCapacity(categoryCount + 1);
		categories[categoryCount++] = c;
		c.addProductInternal(this); // link other side w/o recursion
	}

	public void removeCategory(Category c) {
		if (c == null)
			return;
		int idx = indexOfCategory(c);
		if (idx < 0)
			return;
		shiftLeftCategories(idx);
		categoryCount--;
		c.removeProductInternal(this);
	}

	/* ---------- internal helpers ---------- */
	void addCategoryInternal(Category c) {
		if (c == null)
			return;
		if (indexOfCategory(c) >= 0)
			return;
		ensureCategoryCapacity(categoryCount + 1);
		categories[categoryCount++] = c;
	}

	void removeCategoryInternal(Category c) {
		int idx = indexOfCategory(c);
		if (idx < 0)
			return;
		shiftLeftCategories(idx);
		categoryCount--;
	}

	private int indexOfCategory(Category c) {
		for (int i = 0; i < categoryCount; i++) {
			if (categories[i] == c)
				return i;
		}
		return -1;
	}

	private void ensureCategoryCapacity(int needed) {
		if (categories.length >= needed)
			return;
		int newCap = Math.max(needed, categories.length * 2);
		Category[] bigger = new Category[newCap];
		for (int i = 0; i < categoryCount; i++)
			bigger[i] = categories[i];
		categories = bigger;
	}

	private void shiftLeftCategories(int fromIdx) {
		for (int i = fromIdx; i < categoryCount - 1; i++) {
			categories[i] = categories[i + 1];
		}
		categories[categoryCount - 1] = null;
	}

	@Override
	public String toString() {
		return "Product{id=" + productId + ", name='" + name + "', price=" + unitPrice + "}";
	}
}