package com.company.training;

public class DefaultSalaryCalculator implements SalaryCalculator {

	@Override
	public double calculate(Employee e) {
		return e.getSalary() + 5000;
	}
}
