import java.io.*;

public class TWRDemo {
	static class FaultyCloseable implements AutoCloseable {
		@Override
		public void close() throws IOException {
			throw new IOException("Error while closing");
		}
	}

	public static void main(String[] args) {
        try (FaultyCloseable c = new FaultyCloseable()) {
            throw new IOException("Main operation failed");
        } catch (IOException e) {
            System.out.println("Caught: " + e.getMessage());
            for (Throwable sup : e.getSuppressed()) {
                System.out.println("Suppressed: " + sup.getMessage());
            }
        }
    }
}
