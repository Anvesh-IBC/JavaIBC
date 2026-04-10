
public class HelloSupplier implements MySupplier<String> {
	@Override
	public String get() {
		return "Hello from Supplier!";
	}
}
