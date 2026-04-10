package com.hms.reports.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.hms.dao.InMemoryPatientDAO;
import com.hms.dao.PatientDAO;
import com.hms.domain.Patient;
import com.hms.reports.DailyCensusReport;

class DailyCensusReportTest {

	@Test
	void generatesWithHeaderBodyFooter() throws Exception {
		PatientDAO pdao = new InMemoryPatientDAO();
		pdao.save(new Patient("P-1", "Arun"));
		pdao.save(new Patient("P-2", "Neha"));

		DailyCensusReport report = new DailyCensusReport(pdao);
		Path tempDir = Files.createTempDirectory("hms-report-");
		File out = tempDir.resolve("daily.txt").toFile();

		report.generate(out);
		String content = new String(Files.readAllBytes(out.toPath()), "UTF-8");

		assertTrue(content.contains("=== HMS Report ==="));
		assertTrue(content.contains("Total Patients:2"));
		assertTrue(content.contains("=== End ==="));
	}
}
