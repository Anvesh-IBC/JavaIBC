import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessDemo {
	public static void main(String[] args) {
		File file = new File("demo-data/raf-demo.bin");
		file.getParentFile().mkdirs();

		try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
			// Write at the beginning
			raf.writeInt(100); // bytes 0..3
			raf.writeInt(200); // bytes 4..7

			// Seek to first integer (offset 0) and update it
			raf.seek(0);
			raf.writeInt(999);

			// Read back both ints
			raf.seek(0);
			int first = raf.readInt();
			int second = raf.readInt();

			System.out.println("first=" + first + ", second=" + second);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}