package com.ibc.training.util;

import com.ibc.training.domain.Course;
import com.ibc.training.domain.CourseLevel;

public final class PassByValueDemo {
	private PassByValueDemo() {
	}

	public static void modifyPrimitive(int x) {
		x = x + 10;
	}

	public static void modifyObjectField(Course c) {
		c.setTitle(c.getTitle() + " [Updated]");
	}

	public static void reassignObject(Course c) {
		c = new Course("TMP-000", "Temp", CourseLevel.BEGINNER, Course.DEFAULT_FEE, 1);
	}
}
