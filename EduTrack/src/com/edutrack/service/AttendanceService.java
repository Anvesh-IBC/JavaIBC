package com.edutrack.service;

import com.edutrack.exception.EntityNotFoundException;
import com.edutrack.model.AttendanceRecord;
import com.edutrack.repository.AttendanceRepository;
import com.edutrack.repository.file.FileAttendanceRepository;

import java.io.IOException;
import java.time.LocalDate;

public class AttendanceService {
	private final AttendanceRepository repo = new FileAttendanceRepository();
	private final StudentService studentService = new StudentService();

	public void mark(int studentId, LocalDate date, boolean present) throws IOException, EntityNotFoundException {
		studentService.getById(studentId); // ensure student exists
		AttendanceRecord rec = repo.findByStudentId(studentId);
		if (rec == null)
			rec = new AttendanceRecord(studentId);
		rec.mark(date, present);
		repo.save(studentId, rec);
	}

	public double percentage(int studentId, LocalDate from, LocalDate to) throws IOException, EntityNotFoundException {
		studentService.getById(studentId); // ensure student exists
		AttendanceRecord rec = repo.findByStudentId(studentId);
		if (rec == null)
			return 0.0;
		return rec.percentage(from, to);
	}
}
