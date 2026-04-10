import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class OrdersEndToEnd {
    public static void main(String[] args) {
        File dir = new File("demo-data");
        dir.mkdirs();
        File csv = new File(dir, "orders.csv");
        File report = new File(dir, "summary.txt");
        File ser = new File(dir, "summary.ser");

        createSampleCSV(csv);
        OrderSummary summary = summarize(csv);
        writeReport(report, summary);
        patchReportNumber(report, "totalQty", 999); // RandomAccessFile demo
        serializeSummary(ser, summary);

        System.out.println("Done. Check demo-data folder.");
    }

    private static void createSampleCSV(File csv) {
        try (Writer w = new OutputStreamWriter(new FileOutputStream(csv), StandardCharsets.UTF_8)) {
            w.write("id,item,qty\n");
            w.write("O-1,Pizza,2\n");
            w.write("O-2,Burger,1\n");
            w.write("O-3,Pizza,3\n");
            w.write("O-4,Pasta,1\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static OrderSummary summarize(File csv) {
        OrderSummary s = new OrderSummary();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csv), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;
                String item = parts[1].trim();
                int qty = Integer.parseInt(parts[2].trim());
                s.totalOrders++;
                s.totalQty += qty;
                s.itemCount.merge(item, qty, Integer::sum);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static void writeReport(File report, OrderSummary s) {
        try (PrintWriter out = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(report), StandardCharsets.UTF_8), true)) {
            out.println("=== Order Summary ===");
            out.println("totalOrders: " + s.totalOrders);
            out.println("totalQty: " + s.totalQty);
            out.println("Items:");
            for (Map.Entry<String, Integer> e : s.itemCount.entrySet()) {
                out.println("  - " + e.getKey() + ": " + e.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Simple patch: find "totalQty: " text, then overwrite the number (pad if needed)
    private static void patchReportNumber(File report, String field, int newValue) {
        String marker = field + ": ";
        try (RandomAccessFile raf = new RandomAccessFile(report, "rw")) {
            String line;
            long posBeforeLine;
            while ((posBeforeLine = raf.getFilePointer()) >= 0) {
                line = raf.readLine();
                if (line == null) break;
                // Lines from readLine() are ISO-8859-1 bytes; but we only search ASCII marker
                if (line.startsWith(marker)) {
                    long numberPos = posBeforeLine + marker.length();
                    // Overwrite the digits (assumes single-line number and enough space)
                    raf.seek(numberPos);
                    String newText = String.valueOf(newValue);
                    raf.write(newText.getBytes("UTF-8"));
                    // If newText shorter than old, you might want to pad with spaces
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to patch report: " + e.getMessage());
        }
    }

    private static void serializeSummary(File ser, OrderSummary s) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ser))) {
            oos.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

