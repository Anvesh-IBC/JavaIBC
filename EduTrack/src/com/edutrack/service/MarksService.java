package com.edutrack.service;

import com.edutrack.exception.EntityNotFoundException;
import com.edutrack.exception.InvalidStudentDataException;
import com.edutrack.model.Result;
import com.edutrack.model.Student;
import com.edutrack.repository.MarksRepository;
import com.edutrack.repository.file.FileMarksRepository;

import java.io.IOException;
import java.util.Arrays;

public class MarksService {
	private final MarksRepository repo = new FileMarksRepository();
	private final StudentService studentService = new StudentService();

	public void addMarks(int studentId, int m1, int m2, int m3) throws IOException, EntityNotFoundException, InvalidStudentDataException  {
		Student s = studentService.getById(studentId); // validate existence
		Result r = new Result();
		r.setMarks(Arrays.asList(m1, m2, m3));
		r.compute();
		repo.save(studentId, r);
		// attach to student object in-memory (optional)
		s.setResult(r);
		studentService.updateStudent(s); // persist any change (no-op if same)
	}

	// Overloaded
	public void addMarks(int studentId, int[] marks) throws IOException, EntityNotFoundException, InvalidStudentDataException  {
		if (marks == null || marks.length != 3)
			throw new IllegalArgumentException("Need exactly 3 marks.");
		addMarks(studentId, marks[0], marks[1], marks[2]);
	}

	public Result getResult(int studentId) throws IOException, EntityNotFoundException {
		Result r = repo.findByStudentId(studentId);
		if (r == null)
			throw new EntityNotFoundException("No result for student " + studentId);
		return r;
	}
}
