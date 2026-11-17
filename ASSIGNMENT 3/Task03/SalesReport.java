import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SalesReport {
    static void manual(List<Path> inputs, Path output) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(output);
            for (Path input : inputs) {
                BufferedReader reader = Files.newBufferedReader(input);
                String line;
                while ((line = reader.readLine()) != null) writer.write(line + System.lineSeparator());
                // Deliberate resource leak: reader is not closed if read/write fails.
                reader.close();
            }
        } finally {
            if (writer != null) writer.close();
        }
    }

    static void safe(List<Path> inputs, Path output) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            for (Path input : inputs) {
                try (BufferedReader reader = Files.newBufferedReader(input)) {
                    String line;
                    while ((line = reader.readLine()) != null)
                        writer.write(line + System.lineSeparator());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of("Task03/data");
        Files.createDirectories(dir);
        Path one = dir.resolve("store1.txt");
        Path two = dir.resolve("store2.txt");
        Files.writeString(one, "Store 1: 1200\n");
        Files.writeString(two, "Store 2: 950\n");
        List<Path> files = List.of(one, two);
        manual(files, dir.resolve("manual-report.txt"));
        safe(files, dir.resolve("safe-report.txt"));
        System.out.println("Manual and try-with-resources reports generated.");
        System.out.println("Reports equal: " + Files.readString(dir.resolve("manual-report.txt"))
                .equals(Files.readString(dir.resolve("safe-report.txt"))));
    }
}
