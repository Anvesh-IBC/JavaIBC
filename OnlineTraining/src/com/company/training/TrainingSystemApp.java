package com.company.training;

public class TrainingSystemApp {

	public static void main(String[] args) {

		// Primitive types
		int id = 101;
		double sal = 75000;
		boolean active = true;

		// Employees
		Employee e1 = new Employee(101, "Ravi");
		e1.setSalary(sal);

		Trainer t1 = new Trainer(201, "Suresh", "Java");
		t1.setSalary(100000.0);

		// Department
		Department it = new Department("IT");
		it.addEmployee(e1);
		it.addEmployee(t1);

		// Training
		Training java = new Training("Core Java");
		java.enroll(e1);
		java.enroll(t1);

		// Payroll
		SalaryCalculator calc = new DefaultSalaryCalculator();
		PayrollService payroll = new PayrollService(calc);
		payroll.processSalary(e1);

		// Display
		it.showEmployees();
		java.showParticipants();

		// Polymorphism
		Employee emp = new Trainer(301, "Anita", "Spring");
		if (emp instanceof Trainer) {
			Trainer t = (Trainer) emp;
			t.train();
		}
		//((Trainer) emp).train();
	}
}
