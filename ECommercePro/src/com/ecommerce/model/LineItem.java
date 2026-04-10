package com.ecommerce.model;

public class LineItem {
	
	// ----- Fields -----
	
	private final Product product; // HAS-A Product  (composition)
	private int quantity;
	
	// ------ Constructor ------

	public LineItem(Product product, int quantity) {
		if(product == null) {
			throw new IllegalArgumentException("Product cannot be null");
			
		}
		if(quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be > 0");
		}
		this.product = product;
		this.quantity = quantity;
	}

	// ------- Getters -------
	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}
	
	// ------- Business Methods -------

	// Increase quantity of the same product

	public void increaseQuantity(int qty) {
		if (qty <= 0) {
			throw new IllegalArgumentException("Increase qty must be > 0");
		}
		this.quantity += qty;
	}

	// Calculates total price for this line item
	
	public double getLineTotal() {
		return product.getPrice() * quantity;
	}

	@Override
	public String toString() {
		return "LineItem [Product=" + product.getId() + ", Qty=" + quantity + ", Total=" + getLineTotal() + "]";
	}

}
