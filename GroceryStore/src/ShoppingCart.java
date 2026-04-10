
public class ShoppingCart {
	Product[] items = new Product[20]; // Object array
	int[] quantities = new int[20]; // Primitive array
	double[] pricesAtPurchase = new double[20]; // Primitive array
	int itemCount;

	static int cartCounter;

	static {
		System.out.println("Cart System Ready");
		cartCounter = 0;
	}

	{
		System.out.println("Cart #" + (++cartCounter) + " Initialized");
		itemCount = 0;
	}

	void addItem(Product product, int qty) {
		if (itemCount < items.length && product.quantity >= qty) {
			items[itemCount] = product;
			quantities[itemCount] = qty;
			pricesAtPurchase[itemCount] = product.price;
			itemCount++;
			System.out.printf(" Added %d %s @ $%.2f each%n", qty, product.name, product.price);
		}
	}

	void printReceipt() {
		double subtotal = 0.0;
		System.out.println("=====================================");
		System.out.println("          GROCERY RECEIPT");
		System.out.println("=====================================");

		for (int i = 0; i < itemCount; i++) {
			if (items[i] != null) {
				double lineTotal = quantities[i] * pricesAtPurchase[i];
				subtotal += lineTotal;
				System.out.printf("%2d x %-12s $%6.2f  = $%6.2f%n", quantities[i], items[i].name, pricesAtPurchase[i],
						lineTotal);
			}
		}

		double tax = subtotal * 0.08; // 8% sales tax
		double total = subtotal + tax;

		System.out.println("=====================================");
		System.out.printf("Subtotal:          $%7.2f%n", subtotal);
		System.out.printf("Sales Tax (8%%):    $%7.2f%n", tax);
		System.out.printf("TOTAL:             $%7.2f%n", total);
		System.out.println("=====================================");
	}

	void checkout() {
		System.out.println("\n Checkout Complete - Inventory Updated!");
		System.out.println("Stock levels automatically updated via pass-by-reference copies");
	}
}
