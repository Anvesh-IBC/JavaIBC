import java.io.File;

public class FileFilterJava7_Main {

	public static void main(String[] args) {
		File dir = new File("C:\\Users\\vs82375\\Documents");
		TxtFileFilter filter = new TxtFileFilter();
		File[] contents = dir.listFiles(filter);
		if (contents == null) {
			System.out.println("Directory not found or an I/O error occurred.");
			return;
		}

		for (File file : contents) {
			System.out.println(file.getAbsolutePath());
		}
	}

}
