package ArraysObjectAverageCalculation;

public class Student {
	String name;
	int[] marks = new int[5]; // Can store 5 marks

	public Student(String name) {
		this.name = name;
		for (int i = 0; i < marks.length; i++) {
			marks[i] = 60 + i * 5; // Set some demo marks
		}
	}

	public double getAverage() {
		int sum = 0;
		for (int mark : marks) {
			sum += mark;
		}
		return sum / (double) marks.length;
	}

	public static void main(String[] args) {
		Student s = new Student("Anil");
		System.out.println("Name: " + s.name);
		for (int i = 0; i < s.marks.length; i++) {
			System.out.println("Subject " + (i + 1) + ": " + s.marks[i]);
		}
		System.out.println("Average: " + s.getAverage());
	}
}
