package PassByValue.PrimitiveVsObject;

class Person {
	String name;

	Person(String name) {
		this.name = name;
	}
}

public class PassByValueReferenceDemo {

    public static void main(String[] args) {
        // Primitive example: pass-by-value
        int num = 10;
        System.out.println("Before modifyPrimitive: num = " + num);
        modifyPrimitive(num);
        System.out.println("After modifyPrimitive: num = " + num);

        System.out.println("-----------------------");

        // Object example: pass-by-value of reference
        Person p = new Person("Ravi");
        System.out.println("Before modifyObject: p.name = " + p.name);
        modifyObject(p);
        System.out.println("After modifyObject: p.name = " + p.name);

        System.out.println("-----------------------");

        // Trying to reassign the object reference inside the method
        System.out.println("Before reassignObject: p.name = " + p.name);
        reassignObject(p);
        System.out.println("After reassignObject: p.name = " + p.name);
    }

    static void modifyPrimitive(int number) {
        number = number + 50;
        System.out.println("Inside modifyPrimitive: number = " + number);
    }

    static void modifyObject(Person person) {
        person.name = "Sita"; // Changing object's internal state
        System.out.println("Inside modifyObject: person.name = " + person.name);
    }

    static void reassignObject(Person person) {
        person = new Person("Mohit"); // Reassigning local copy of reference
        System.out.println("Inside reassignObject: person.name = " + person.name);
    }
}

