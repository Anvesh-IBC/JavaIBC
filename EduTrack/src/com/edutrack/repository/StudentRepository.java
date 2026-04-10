package com.edutrack.repository;

import com.edutrack.model.Student;

import java.io.IOException;
import java.util.List;

public interface StudentRepository {
	void save(Student s) throws IOException;

	void update(Student s) throws IOException;

	void delete(int id) throws IOException;

	Student findById(int id) throws IOException;

	List<Student> findAll() throws IOException;
}