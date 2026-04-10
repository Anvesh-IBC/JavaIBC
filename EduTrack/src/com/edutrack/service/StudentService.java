package com.edutrack.service;

import com.edutrack.exception.EntityNotFoundException;
import com.edutrack.exception.InvalidStudentDataException;
import com.edutrack.model.Student;
import com.edutrack.repository.StudentRepository;
import com.edutrack.repository.file.FileStudentRepository;
import com.edutrack.util.IdGenerator;
import com.edutrack.util.Validators;

import java.io.IOException;
import java.util.List;

public class StudentService {
	private final StudentRepository repo = new FileStudentRepository();

	public Student addStudent(String name, String email, String phoneStr, byte age)
			throws InvalidStudentDataException, IOException {
		if (!Validators.isValidName(name))
			throw new InvalidStudentDataException("Name required (min 2 chars).");
		if (!Validators.isValidEmail(email))
			throw new InvalidStudentDataException("Invalid email.");
		if (!Validators.isValidPhone(phoneStr))
			throw new InvalidStudentDataException("Phone must be 10 digits.");
		if (!Validators.isValidAge(age))
			throw new InvalidStudentDataException("Age must be >= 16.");

		int id = IdGenerator.nextStudentId();
		long phone = Long.parseLong(phoneStr);
		Student s = new Student(id, name, email, phone, age);
		repo.save(s);
		return s;
	}

	public void updateStudent(Student s) throws InvalidStudentDataException, IOException {
		if (s == null)
			throw new InvalidStudentDataException("Null student.");
		if (!Validators.isValidEmail(s.getEmail()))
			throw new InvalidStudentDataException("Invalid email.");
		repo.update(s);
	}

	public void deleteStudent(int id) throws IOException {
		repo.delete(id);
	}

	public Student getById(int id) throws IOException, EntityNotFoundException {
		Student s = repo.findById(id);
		if (s == null)
			throw new EntityNotFoundException("Student " + id + " not found");
		return s;
	}

	public List<Student> listAll() throws IOException {
		return repo.findAll();
	}
}
