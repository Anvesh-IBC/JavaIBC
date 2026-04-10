package com.ibc.training.shop;

public class Category {
	private final int categoryId;
	private final String name;

	private Product[] products = new Product[4];
	private int productCount = 0;

	public Category(int categoryId, String name) {
		this.categoryId = categoryId;
		this.name = name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}

	public int getProductCount() {
		return productCount;
	}

	public Product[] getProductsSnapshot() {
		Product[] copy = new Product[productCount];
		for (int i = 0; i < productCount; i++)
			copy[i] = products[i];
		return copy;
	}

	public void addProduct(Product p) {
		if (p == null)
			return;
		if (indexOfProduct(p) >= 0)
			return;
		ensureProductCapacity(productCount + 1);
		products[productCount++] = p;
		p.addCategoryInternal(this); // prevent infinite recursion
	}

	public void removeProduct(Product p) {
		if (p == null)
			return;
		int idx = indexOfProduct(p);
		if (idx < 0)
			return;
		shiftLeftProducts(idx);
		productCount--;
		p.removeCategoryInternal(this);
	}

	/* ---------- internal helpers ---------- */
	void addProductInternal(Product p) {
		if (p == null)
			return;
		if (indexOfProduct(p) >= 0)
			return;
		ensureProductCapacity(productCount + 1);
		products[productCount++] = p;
	}

	void removeProductInternal(Product p) {
		int idx = indexOfProduct(p);
		if (idx < 0)
			return;
		shiftLeftProducts(idx);
		productCount--;
	}

	private int indexOfProduct(Product p) {
		for (int i = 0; i < productCount; i++) {
			if (products[i] == p)
				return i;
		}
		return -1;
	}

	private void ensureProductCapacity(int needed) {
		if (products.length >= needed)
			return;
		int newCap = Math.max(needed, products.length * 2);
		Product[] bigger = new Product[newCap];
		for (int i = 0; i < productCount; i++)
			bigger[i] = products[i];
		products = bigger;
	}

	private void shiftLeftProducts(int fromIdx) {
		for (int i = fromIdx; i < productCount - 1; i++) {
			products[i] = products[i + 1];
		}
		products[productCount - 1] = null;
	}

	@Override
	public String toString() {
		return "Category{" + name + "}";
	}
}