import java.io.*;
import java.nio.charset.Charset;

public class WordCount {
	public static void main(String[] args) {
		File file = new File("demo-data/note.txt");
		file.getParentFile().mkdirs();

		// Create a sample file
		try (Writer w = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8"))) {
			w.write("Hello world\nThis is a test\nJava 8 I/O\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read and count words
		int words = 0;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					words += line.trim().split("\\s+").length;
				}
			}
			System.out.println("Total words: " + words);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}