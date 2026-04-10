package com.edutrack.util;

import com.edutrack.model.AttendanceRecord;
import com.edutrack.model.Grade;
import com.edutrack.model.Result;
import com.edutrack.model.Student;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CsvMapper {
	private CsvMapper() {
	}

	// students.csv: id,name,email,phone,age,enrolledDate,feesPaid
	public static Student toStudent(String line) {
		try {
			String[] p = line.split(",", -1);
			Student s = new Student();
			s.setId(Integer.parseInt(p[0]));
			s.setName(p[1]);
			s.setEmail(p[2]);
			s.setPhone(Long.parseLong(p[3]));
			s.setAge(Byte.parseByte(p[4]));
			s.setEnrolledDate(LocalDate.parse(p[5]));
			s.setFeesPaid(Boolean.parseBoolean(p[6]));
			return s;
		} catch (Exception e) {
			return null;
		}
	}

	public static String fromStudent(Student s) {
		StringBuilder sb = new StringBuilder(128);
		sb.append(s.getId()).append(',').append(escape(s.getName())).append(',').append(escape(s.getEmail()))
				.append(',').append(s.getPhone()).append(',').append(s.getAge()).append(',').append(s.getEnrolledDate())
				.append(',').append(s.isFeesPaid());
		return sb.toString();
	}

	private static String escape(String v) {
		return v == null ? "" : v.replace(",", " "); // simple escape
	}

	// marks.csv: studentId|m1;m2;m3|percentage|grade
	public static Map.Entry<Integer, Result> toMarks(String line) {
		try {
			String[] p = line.split("\\|");
			int id = Integer.parseInt(p[0]);
			String[] ms = p[1].split(";");
			List<Integer> marks = new ArrayList<>();
			for (String m : ms)
				marks.add(Integer.parseInt(m));
			double pct = Double.parseDouble(p[2]);
			Grade g = Grade.valueOf(p[3]);

			Result r = new Result();
			r.setMarks(marks);
			// compute() to keep grade logic uniform (overrides read numbers)
			r.compute();
			// Keep file values if you prefer:
			// (But we'll just recompute)
			return new AbstractMap.SimpleEntry<>(id, r);
		} catch (Exception e) {
			return null;
		}
	}

	public static String fromMarks(int studentId, Result r) {
		StringBuilder sb = new StringBuilder(64);
		sb.append(studentId).append('|');
		List<Integer> ms = r.getMarks();
		for (int i = 0; i < ms.size(); i++) {
			sb.append(ms.get(i));
			if (i < ms.size() - 1)
				sb.append(';');
		}
		sb.append('|').append(String.format("%.2f", r.getPercentage())).append('|').append(r.getGrade());
		return sb.toString();
	}

	// attendance.csv: studentId|YYYY-MM-DD:true;YYYY-MM-DD:false;...
	public static AttendanceRecord toAttendance(String line) {
		try {
			String[] p = line.split("\\|", -1);
			int id = Integer.parseInt(p[0]);
			AttendanceRecord rec = new AttendanceRecord(id);
			if (p.length > 1 && !p[1].trim().isEmpty()) {
				String[] pairs = p[1].split(";");
				for (String pr : pairs) {
					String[] kv = pr.split(":");
					LocalDate d = LocalDate.parse(kv[0]);
					boolean present = Boolean.parseBoolean(kv[1]);
					rec.mark(d, present);
				}
			}
			return rec;
		} catch (Exception e) {
			return null;
		}
	}

	public static String fromAttendance(AttendanceRecord rec) {
		StringBuilder sb = new StringBuilder(64);
		sb.append(rec.getStudentId()).append('|');
		boolean first = true;
		for (Map.Entry<LocalDate, Boolean> e : rec.getCalendar().entrySet()) {
			if (!first)
				sb.append(';');
			sb.append(e.getKey()).append(':').append(e.getValue());
			first = false;
		}
		return sb.toString();
	}
}