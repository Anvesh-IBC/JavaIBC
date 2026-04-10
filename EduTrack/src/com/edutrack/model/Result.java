package com.edutrack.model;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private final List<Integer> marks = new ArrayList<>();
	private double percentage;
	private Grade grade;

	public List<Integer> getMarks() {
		return marks;
	}

	public double getPercentage() {
		return percentage;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setMarks(List<Integer> list) {
		marks.clear();
		if (list != null)
			marks.addAll(list);
	}

	// Operators: arithmetic, relational, ternary
	public void compute() {
		if (marks.size() == 0) {
			percentage = 0;
			grade = Grade.F;
			return;
		}
		int sum = 0;
		for (Integer m : marks)
			sum += (m != null ? m : 0);
		percentage = sum / (marks.size() * 1.0);
		if (percentage >= 90)
			grade = Grade.A;
		else if (percentage >= 75)
			grade = Grade.B;
		else if (percentage >= 60)
			grade = Grade.C;
		else if (percentage >= 40)
			grade = Grade.D;
		else
			grade = Grade.F;
	}

	@Override
	public String toString() {
		return "Result{marks=" + marks + ", percentage=" + String.format("%.2f", percentage) + ", grade=" + grade + "}";
	}
}
