import java.io.*;

class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String id;
	private final String item;
	private final int quantity;
	private transient String internalNote; // won't be serialized
//	private String internalNote; // won't be serialized

	Order(String id, String item, int quantity, String internalNote) {
		this.id = id;
		this.item = item;
		this.quantity = quantity;
		this.internalNote = internalNote;
	}

	@Override
	public String toString() {
		return "Order{id='" + id + "', item='" + item + "', quantity=" + quantity + ", internalNote='" + internalNote
				+ "'}";
	}
}