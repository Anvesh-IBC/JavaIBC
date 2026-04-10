
public class GroceryDemo {
	public static void main(String[] args) {
		System.out.println("=== Grocery Store Management System ===\n");

		// Test 1: Primitive literals + uninitialized ERROR demo
		testPrimitiveLiterals();
		System.out.println();

		// Test 2: Pass-by-value proof
		testPassByValue();
		System.out.println();

		// Test 3: Arrays + initialization blocks + full workflow
		testCompleteSystem();
	}

	// 1. ALL PRIMITIVE LITERALS + Uninitialized ERROR
	static void testPrimitiveLiterals() {
		System.out.println("1. Primitive Literals:");
		int productId = 1001;
		byte discount = 15;
		short shelf = 42;
		int stockQty = 250;
		long barcode = 1234567890123456L; // L suffix
		float weight = 2.5f; // f suffix
		double price = 3.99;
		char category = 'F';
		boolean inStock = true;

		System.out.printf("ID=%d, disc=%d%%, shelf=%d, qty=%d, barcode=%dL, wt=%.1ff, price=%.2f, cat=%c, stock=%b%n",
				productId, discount, shelf, stockQty, barcode, weight, price, category, inStock);

		// UNINITIALIZED ERROR (commented to show compile failure)
		// int uninitQty; updateStock(uninitQty); // COMPILE ERROR!
	}

	// 2. PASS-BY-VALUE PROOF
	static void testPassByValue() {
		System.out.println("2. Pass-by-Value Test:");
		Product milk = new Product(1001, "Milk", 2.99, 50);
		int originalQty = milk.quantity;
		double originalPrice = milk.price;

		System.out.printf("Before: Qty=%d, Price=%.2f%n", originalQty, originalPrice);

		addToCart(milk, 3); // Object mutates
		updatePrice(originalPrice); // Primitive unchanged

		System.out.printf("After:  Qty=%d, Price=%.2f%n", milk.quantity, milk.price);
		System.out.printf("Original Qty=%d (CHANGED), Price=%.2f (UNCHANGED)%n", originalQty, originalPrice);
	}

	// 3. COMPLETE GROCERY SYSTEM
	static void testCompleteSystem() {
		System.out.println("\n3. Complete Grocery System:");

		// Create products
		Product apple = new Product(1001, "Apple", 2.99, 100);
		Product milk = new Product(1002, "Milk", 3.49, 25);
		Product bread = new Product(1003, "Bread", 1.99, 50);

		// Shopping cart workflow
		ShoppingCart cart = new ShoppingCart();
		cart.addItem(apple, 5);
		cart.addItem(milk, 2);
		cart.addItem(bread, 3);

		System.out.println("\n--- RECEIPT ---");
		cart.printReceipt();
		cart.checkout();

		System.out.printf("Stock after checkout - Apple: %d, Milk: %d, Bread: %d%n", apple.quantity, milk.quantity,
				bread.quantity);
	}

	static void addToCart(Product product, int qty) {
		if (product.quantity >= qty) {
			product.quantity -= qty;
			System.out.printf("Added %d %s to cart%n", qty, product.name);
		}
	}

	static void updatePrice(double price) {
		price = 0.99; // Local copy only - original UNCHANGED
		System.out.printf("Method price updated to %.2f (local copy)%n", price);
	}
}
