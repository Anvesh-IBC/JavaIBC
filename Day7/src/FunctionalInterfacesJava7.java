
public class FunctionalInterfacesJava7 {
public static void main(String[] args) {
	//Consumer<String>
	MyConsumer<String> consumer = new PrintlnConsumer();
	consumer.accept("Hello LE!");
	
	//Supplier<String>
	MySupplier<String> supplier = new HelloSupplier();
	
	//Predicate<Integer>
	MyPredicate<Integer>even = new EvenPredicate();
	System.out.println(even.test(24)); //true
	System.out.println(even.test(15)); //false
	
	//BiFunction<Integer, Integer, Integer> - max of two numbers
	MyBiFunction<Integer, Integer, Integer> maxFn = new MaxIntBiFunction();
	System.out.println(maxFn.apply(25, 14)); //25
}
}
