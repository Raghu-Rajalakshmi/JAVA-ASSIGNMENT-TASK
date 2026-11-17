import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentResultProcessor {
    record Result(String file, int score) {}
    record Outcome(String file, Result result, String error) {
        static Outcome success(String f, Result r) { return new Outcome(f,r,null); }
        static Outcome failure(String f, String e) { return new Outcome(f,null,e); }
    }

    static Outcome process(Path file) {
        try {
            String raw = Files.readString(file).trim();
            int score = Integer.parseInt(raw);
            if (score < 0 || score > 100) throw new IllegalArgumentException("Score outside 0..100");
            return Outcome.success(file.getFileName().toString(), new Result(file.toString(), score));
        } catch (IOException | IllegalArgumentException e) {
            return Outcome.failure(file.getFileName().toString(), e.getClass().getSimpleName()+": "+e.getMessage());
        }
    }

    static void run(Path directory, int poolSize) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        ConcurrentMap<String, Outcome> outcomes = new ConcurrentHashMap<>();
        List<Future<Outcome>> futures = new ArrayList<>();
        long start = System.nanoTime();
        try {
            try (var paths = Files.list(directory)) {
                paths.filter(p -> p.toString().endsWith(".txt")).forEach(p ->
                    futures.add(pool.submit(() -> process(p))));
            } catch (IOException e) {
                System.out.println("Cannot list input directory: " + e.getMessage());
                return;
            }
            for (Future<Outcome> future : futures) {
                try {
                    Outcome outcome = future.get();
                    outcomes.put(outcome.file(), outcome);
                } catch (ExecutionException e) {
                    outcomes.put("unexpected-" + outcomes.size(),
                            Outcome.failure("unexpected", String.valueOf(e.getCause())));
                }
            }
            long successes = outcomes.values().stream().filter(o -> o.result()!=null).count();
            long failures = outcomes.size() - successes;
            long elapsed = System.nanoTime() - start;
            System.out.printf("pool=%d total=%d success=%d failure=%d elapsed_ms=%.3f%n",
                    poolSize, outcomes.size(), successes, failures, elapsed/1_000_000.0);
            outcomes.values().stream().filter(o -> o.error()!=null)
                    .sorted(Comparator.comparing(Outcome::file))
                    .forEach(o -> System.out.println("FAILED " + o.file() + ": " + o.error()));
        } finally {
            pool.shutdown();
            if (!pool.awaitTermination(30, TimeUnit.SECONDS)) pool.shutdownNow();
        }
    }

    public static void main(String[] args) throws Exception {
        Path dir = Path.of("Task07/input");
        Files.createDirectories(dir);
        for (int i=1; i<=8; i++) Files.writeString(dir.resolve("score"+i+".txt"), String.valueOf(50+i));
        Files.writeString(dir.resolve("corrupt.txt"), "not-a-number");
        Files.deleteIfExists(dir.resolve("missing.txt")); // absent file is not listed; add a missing path to test separately
        for (int threads : new int[]{1,2,4,8}) run(dir, threads);
    }
}
