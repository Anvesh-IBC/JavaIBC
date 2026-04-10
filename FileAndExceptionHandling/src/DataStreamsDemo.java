import java.io.*;

public class DataStreamsDemo {
	public static void main(String[] args) {
		File file = new File("demo-data/metrics.bin");
		file.getParentFile().mkdirs();

		// Write primitives
		try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {

			dos.writeInt(42);
			dos.writeDouble(99.95);
			dos.writeBoolean(true);
			dos.writeUTF("Hello UTF");
			System.out.println("Wrote binary data.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read them back in the same order
		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {

			int i = dis.readInt();
			double d = dis.readDouble();
			boolean b = dis.readBoolean();
			String s = dis.readUTF();

			System.out.printf("Read: i=%d, d=%f, b=%b, s=%s%n", i, d, b, s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}