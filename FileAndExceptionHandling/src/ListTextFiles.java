import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListTextFiles {
    public static void main(String[] args) {
        File dir = new File("demo-data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Filter for .txt files (case-insensitive)
        FilenameFilter txtFilter = (d, name) -> name.toLowerCase().endsWith(".txt");
        File[] files = dir.listFiles(txtFilter);
        if (files == null) {
            System.out.println("Directory not found or I/O error.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (File f : files) {
            System.out.printf("Name=%s, Size=%d bytes, LastModified=%s, Readable=%b, Writable=%b%n",
                    f.getName(),
                    f.length(),
                    sdf.format(new Date(f.lastModified())),
                    f.canRead(), f.canWrite());
        }
    }
}
