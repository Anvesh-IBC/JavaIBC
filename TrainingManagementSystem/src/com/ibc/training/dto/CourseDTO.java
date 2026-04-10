package com.ibc.training.dto;

import com.ibc.training.domain.Course;
import com.ibc.training.util.FormattingUtil;

import java.util.Locale;

public class CourseDTO {
	public String code;
	public String title;
	public String level;
	public String feeFormatted;
	public int hours;

	public static CourseDTO fromCourse(Course c) {
		CourseDTO dto = new CourseDTO();
		dto.code = c.getCode();
		dto.title = c.getTitle();
		dto.level = c.getLevel().name();
		dto.feeFormatted = FormattingUtil.formatCurrency(c.getFee(), Locale.forLanguageTag("en-IN"));
		dto.hours = c.getHours();
		return dto;
	}
}
