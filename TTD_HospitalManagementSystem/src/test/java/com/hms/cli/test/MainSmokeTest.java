package com.hms.cli.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.hms.cli.Main;

class MainSmokeTest {

	@Test
	void main_runsEndToEnd() throws Exception {
		Main.main(new String[0]); // Should not hang or throw

		File f = new File("reports/daily.txt");
		assertTrue(f.exists());
		// clean up (best-effort)
		f.delete();
		f.getParentFile().delete();
	}
}
