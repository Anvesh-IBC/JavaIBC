package com.edutrack.repository.file;

import com.edutrack.model.AttendanceRecord;
import com.edutrack.repository.AttendanceRepository;
import com.edutrack.util.CsvMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FileAttendanceRepository implements AttendanceRepository {
	private final Path dataDir = Paths.get("data");
	private final Path file = dataDir.resolve("attendance.csv");

	public FileAttendanceRepository() {
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
	public synchronized void save(int studentId, AttendanceRecord rec) throws IOException {
		Map<Integer, AttendanceRecord> map = loadAll();
		map.put(studentId, rec);
		writeAll(map);
	}

	@Override
	public AttendanceRecord findByStudentId(int studentId) throws IOException {
		return loadAll().get(studentId);
	}

	private Map<Integer, AttendanceRecord> loadAll() throws IOException {
		Map<Integer, AttendanceRecord> map = new HashMap<>();
		try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				AttendanceRecord rec = CsvMapper.toAttendance(line);
				if (rec != null)
					map.put(rec.getStudentId(), rec);
			}
		}
		return map;
	}

	private void writeAll(Map<Integer, AttendanceRecord> map) throws IOException {
		Path tmp = file.resolveSibling("attendance.tmp");
		try (BufferedWriter bw = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING)) {
			for (AttendanceRecord rec : map.values()) {
				bw.write(CsvMapper.fromAttendance(rec));
				bw.newLine();
			}
		}
		Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
	}
}