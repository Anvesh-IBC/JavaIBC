package com.edutrack.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AttendanceRecord {
	private int studentId;
	private final Map<LocalDate, Boolean> calendar = new HashMap<>();

	public AttendanceRecord() {
	}

	public AttendanceRecord(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Map<LocalDate, Boolean> getCalendar() {
		return calendar;
	}

	public void mark(LocalDate date, boolean present) {
		calendar.put(date, present);
	}

	public double percentage(LocalDate from, LocalDate to) {
		if (from == null || to == null || from.isAfter(to))
			return 0.0;
		int days = 0, presents = 0;
		LocalDate d = from;
		while (!d.isAfter(to)) {
			Boolean p = calendar.get(d);
			if (p != null) {
				days++;
				if (p)
					presents++;
			}
			d = d.plusDays(1);
		}
		return days == 0 ? 0.0 : (presents * 100.0) / days;
	}
}