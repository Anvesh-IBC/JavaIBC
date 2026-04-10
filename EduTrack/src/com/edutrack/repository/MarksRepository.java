package com.edutrack.repository;

import com.edutrack.model.Result;

import java.io.IOException;

public interface MarksRepository {
	void save(int studentId, Result r) throws IOException;

	Result findByStudentId(int studentId) throws IOException;
}