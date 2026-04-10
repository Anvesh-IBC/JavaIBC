package StackVsHeap;

public class StackHeapDemo {
	public static void main(String[] args) {
		int localNumber = 42; // This lives on stack
		String city = "Delhi"; // Reference lives on stack; actual "Delhi" is on heap
		System.out.println(city + " number: " + localNumber);
	}
}
