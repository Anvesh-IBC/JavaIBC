package com.company.training;

public class PayrollService {

	private SalaryCalculator calculator;

	public PayrollService(SalaryCalculator calculator) {
		this.calculator = calculator;
	}

	public void processSalary(Employee e) {
		double finalSalary = calculator.calculate(e);
		System.out.println("Final Salary: " + finalSalary);
	}
}
