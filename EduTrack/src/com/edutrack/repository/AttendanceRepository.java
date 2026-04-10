package com.edutrack.repository;

import com.edutrack.model.AttendanceRecord;

import java.io.IOException;

public interface AttendanceRepository {
	void save(int studentId, AttendanceRecord rec) throws IOException;

	AttendanceRecord findByStudentId(int studentId) throws IOException;
}