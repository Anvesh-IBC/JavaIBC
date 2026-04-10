package com.edutrack.repository.file;

import com.edutrack.model.Result;
import com.edutrack.repository.MarksRepository;
import com.edutrack.util.CsvMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FileMarksRepository implements MarksRepository {
	private final Path dataDir = Paths.get("data");
	private final Path file = dataDir.resolve("marks.csv");

	public FileMarksRepository() {
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
	public synchronized void save(int studentId, Result r) throws IOException {
		Map<Integer, Result> map = loadAll();
		map.put(studentId, r);
		writeAll(map);
	}

	@Override
	public Result findByStudentId(int studentId) throws IOException {
		return loadAll().get(studentId);
	}

	private Map<Integer, Result> loadAll() throws IOException {
		Map<Integer, Result> map = new HashMap<>();
		try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				Map.Entry<Integer, Result> e = CsvMapper.toMarks(line);
				if (e != null)
					map.put(e.getKey(), e.getValue());
			}
		}
		return map;
	}

	private void writeAll(Map<Integer, Result> map) throws IOException {
		Path tmp = file.resolveSibling("marks.tmp");
		try (BufferedWriter bw = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING)) {
			for (Map.Entry<Integer, Result> e : map.entrySet()) {
				bw.write(CsvMapper.fromMarks(e.getKey(), e.getValue()));
				bw.newLine();
			}
		}
		Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
	}
}