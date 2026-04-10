package com.hms.reports;

import com.hms.dao.PatientDAO;
import com.hms.patterns.template.ReportGenerator;

import java.io.*;

public class DailyCensusReport extends ReportGenerator {
	private final PatientDAO pdao;

	public DailyCensusReport(PatientDAO pdao) {
		this.pdao = pdao;
	}

	@Override
	protected void body(PrintWriter pw) throws IOException {
		pw.println("Total Patients:" + pdao.findAll().size());
	}
}
