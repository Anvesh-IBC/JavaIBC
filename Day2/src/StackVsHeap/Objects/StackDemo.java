package StackVsHeap.Objects;

public class StackDemo {
	public static void main(String[] args) {
// PRIMITIVES - stack only
		int primitive = 10; // stack: value directly stored

// OBJECTS - stack reference + heap object
		String obj = "Hello"; // stack: reference, heap: "Hello" string

// Local object assignment
		String obj2 = obj; // stack: obj2 points to SAME heap object
		obj2 = "World"; // obj2 now points to NEW heap object

		System.out.println("obj=" + obj + ", obj2=" + obj2);
// Output: obj=Hello, obj2=World (independent)
	}
}
