package demos;

public class JavaCodeConventionDemo {

	static final int MAX_LIMIT = 100;

	int employeeId;
	String employeeName;

	void calculateSalary() {
		System.out.println("Salary calculated");
	}

	public static void main(String[] args) {
		JavaCodeConventionDemo obj = new JavaCodeConventionDemo();
		obj.calculateSalary();
	}
}
