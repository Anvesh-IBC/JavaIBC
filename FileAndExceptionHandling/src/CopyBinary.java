import java.io.*;

public class CopyBinary {
	public static void main(String[] args) {
		File src = new File("demo-data/logo.png");
		File dest = new File("demo-data/logo-copy.png");
		
//		File src = new File("C://Users//anves//OneDrive//Desktop//Imgs//one.png");
//		File dest = new File("C://Users//anves//OneDrive//Desktop//Imgs//two.png");

		// Ensure demo-data exists
		src.getParentFile().mkdirs();

		// Copy (if src doesn't exist, this will throw FileNotFoundException)
		try (InputStream in = new BufferedInputStream(new FileInputStream(src));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {

			byte[] buffer = new byte[8 * 1024]; // 8 KB
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			System.out.println("Copy complete.");
		} catch (FileNotFoundException e) {
			System.err.println("Source file not found: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		}
	}
}
