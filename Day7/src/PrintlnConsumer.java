
public class PrintlnConsumer implements MyConsumer<String> {
	@Override
	public void accept(String s) {
		System.out.println(s);
	}
}
