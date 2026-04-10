
public class Product {
	// PRIMITIVE FIELDS (auto-initialized)
	int productId;
	byte discountPercent;
	short shelfNumber;
	int quantity;
	float weight;
	double price;
	char categoryCode;
	boolean perishable;

	// Instance fields
	String name;

	// ARRAYS - All patterns
	String[] ingredients; // 1. Declaration only
	double[] nutritionalValues = new double[5]; // 2. Declare + construct
	String[][] batchInfo = new String[12][]; // 3. Jagged array

	// STATIC MEMBERS
	static int totalProductsInStock;
	static String[] categories = { "Fruits", "Dairy", "Bakery", "Veg" };

	// 4. STATIC BLOCK (runs ONCE)
	static {
		System.out.println("Grocery System Initialized");
		totalProductsInStock = 0;
	}

	// 5. INSTANCE BLOCK (runs per object)
	{
		System.out.println("Product #" + productId + " Created");
		initializeBatchInfo();
		totalProductsInStock++;
	}

	// Constructor
	public Product(int id, String name, double price, int qty) {
		this.productId = id;
		this.name = name;
		this.price = price;
		this.quantity = qty;
		this.categoryCode = name.charAt(0);
		this.perishable = true;
		this.discountPercent = 0;
		this.shelfNumber = (short) (id % 100);
		this.weight = 1.0f;
		System.out.printf("Product: %s @ $%.2f (stock: %d)%n", name, price, qty);
	}

	void initializeBatchInfo() {
		// Jagged array construction
		for (int i = 0; i < batchInfo.length; i++) {
			batchInfo[i] = new String[i % 3 + 1]; // 1-3 batches per month
		}
	}

	void applyBulkDiscount(double... rates) { // Varargs = anonymous array
		System.out.print("Bulk discount rates: ");
		for (double rate : rates) {
			System.out.printf("%.1f%% ", rate);
		}
		System.out.println();
	}

	void printNutrition() {
		System.out.print("Nutrition [Calories, Protein, Carbs, Fat, Fiber]: ");
		for (int i = 0; i < nutritionalValues.length; i++) {
			System.out.printf("%.1f", nutritionalValues[i]);
			if (i < nutritionalValues.length - 1)
				System.out.print(", ");
		}
		System.out.println("]");
	}
}
