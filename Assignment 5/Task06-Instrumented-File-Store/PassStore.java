import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/** UTF-8 pipe-delimited store; backslash and pipe are escaped in text fields. */
public class PassStore {
    private final Path file;
    public PassStore(Path file) { this.file = file; }

    public void add(String passId, String regNo, String name, int route,
                    String stop, String validUntil, double amount) throws IOException {
        if (passId == null || passId.isBlank() || regNo == null || regNo.isBlank()
                || name == null || route <= 0 || stop == null || validUntil == null
                || !Double.isFinite(amount) || amount < 0)
            throw new IllegalArgumentException("Invalid pass fields");
        String row = String.join("|", esc(passId), esc(regNo), esc(name),
                Integer.toString(route), esc(stop), esc(validUntil), Double.toString(amount));
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write(row); w.newLine();
        }
    }

    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = parse(line);
                if (valid(fields) && fields[0].equals(passId)) return fields;
            }
        }
        return null;
    }

    public List<String[]> listByRoute(int route) throws IOException {
        List<String[]> result = new ArrayList<>();
        if (!Files.exists(file)) return result;
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = parse(line);
                if (valid(fields) && Integer.parseInt(fields[3]) == route) result.add(fields);
            }
        }
        return result;
    }

    public static boolean valid(String[] f) {
        if (f == null || f.length != 7) return false;
        try {
            if (f[0].isBlank() || f[1].isBlank() || f[2].isBlank()) return false;
            if (Integer.parseInt(f[3]) <= 0) return false;
            java.time.LocalDate.parse(f[5]);
            double amount = Double.parseDouble(f[6]);
            return Double.isFinite(amount) && amount >= 0;
        } catch (RuntimeException ex) { return false; }
    }

    private static String esc(String s) { return s.replace("\\", "\\\\").replace("|", "\\|"); }
    private static String[] parse(String line) {
        List<String> out = new ArrayList<>(); StringBuilder field = new StringBuilder();
        boolean escaped = false;
        for (int i=0; i<line.length(); i++) {
            char c=line.charAt(i);
            if (escaped) { field.append(c); escaped=false; }
            else if (c=='\\') escaped=true;
            else if (c=='|') { out.add(field.toString()); field.setLength(0); }
            else field.append(c);
        }
        if (escaped) return new String[0];
        out.add(field.toString());
        return out.toArray(new String[0]);
    }

    public static void main(String[] args) throws Exception {
        Path f = Files.createTempFile("passes", ".txt");
        PassStore store = new PassStore(f);
        store.add("P1001","192311001","Arun",12,"Central Stop","2027-05-31",4500);
        Files.writeString(f,"P1002|DAMAGED LINE\n",StandardOpenOption.APPEND);
        store.add("P1003","192311003","Divya",7,"Library","2027-05-31",3800);
        System.out.println(Arrays.toString(store.find("P1003")));
        System.out.println(Arrays.toString(store.find("P1002")));
        System.out.println("Route 7 count: "+store.listByRoute(7).size());
        Files.deleteIfExists(f);
    }
}
