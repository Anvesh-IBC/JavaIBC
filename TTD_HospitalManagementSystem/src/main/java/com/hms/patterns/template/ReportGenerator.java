package com.hms.patterns.template;

import java.io.*;

public abstract class ReportGenerator {
	public final void generate(File out) throws IOException {
		if (out.getParentFile() != null)
			out.getParentFile().mkdirs();
		try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
			header(pw);
			body(pw);
			footer(pw);
		}
	}

	protected void header(PrintWriter pw) {
		pw.println("=== HMS Report ===");
	}

	protected abstract void body(PrintWriter pw) throws IOException;

	protected void footer(PrintWriter pw) {
		pw.println("=== End ===");
	}
}
