package com.edutrack.repository.file;

import com.edutrack.model.Student;
import com.edutrack.repository.StudentRepository;
import com.edutrack.util.CsvMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FileStudentRepository implements StudentRepository {
	private final Path dataDir = Paths.get("data");
	private final Path file = dataDir.resolve("students.csv");

	public FileStudentRepository() {
		try {
			if (!Files.exists(dataDir))
				Files.createDirectories(dataDir);
			if (!Files.exists(file))
				Files.createFile(file);
		} catch (IOException e) {
			throw new RuntimeException("Failed to init data dir", e);
		}
	}

	@Override
	public synchronized void save(Student s) throws IOException {
		List<Student> all = findAll();
		all.add(s);
		writeAll(all);
	}

	@Override
	public synchronized void update(Student s) throws IOException {
		List<Student> all = findAll();
		boolean updated = false;
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getId() == s.getId()) {
				all.set(i, s);
				updated = true;
				break;
			}
		}
		if (!updated)
			all.add(s);
		writeAll(all);
	}

	@Override
	public synchronized void delete(int id) throws IOException {
		List<Student> all = findAll();
		all.removeIf(st -> st.getId() == id);
		writeAll(all);
	}

	@Override
	public Student findById(int id) throws IOException {
		for (Student s : findAll())
			if (s.getId() == id)
				return s;
		return null;
	}

	@Override
	public List<Student> findAll() throws IOException {
		List<Student> list = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				Student s = CsvMapper.toStudent(line);
				if (s != null)
					list.add(s);
			}
		}
		return list;
	}

	private void writeAll(List<Student> all) throws IOException {
		Path tmp = file.resolveSibling("students.tmp");
		try (BufferedWriter bw = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING)) {
			for (Student s : all) {
				bw.write(CsvMapper.fromStudent(s));
				bw.newLine();
			}
		}
		Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
	}
}