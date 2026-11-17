import java.util.*;
import java.util.concurrent.*;

public class ScannerStations {
    static class StationThread extends Thread {
        private final int station, scripts;
        StationThread(int station, int scripts) { super("ThreadStation-" + station); this.station=station; this.scripts=scripts; }
        public void run() { process(station, scripts); }
    }
    static class StationTask implements Runnable {
        private final int station, scripts;
        StationTask(int station, int scripts) { this.station=station; this.scripts=scripts; }
        public void run() { process(station, scripts); }
    }
    static void process(int station, int scripts) {
        try {
            Thread.sleep(40L + (station % 3) * 25L);
            System.out.println("Station " + station + " scanned " + scripts +
                    " scripts on " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Station " + station + " interrupted");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        int total = 0;
        Thread[] stations = new Thread[4];
        for (int i=0; i<4; i++) {
            stations[i] = new StationThread(i+1, 25);
            stations[i].start();
        }
        for (Thread t : stations) t.join();
        total += 4 * 25;
        System.out.println("Thread subclass total: " + total);

        Thread[] runnableStations = new Thread[4];
        for (int i=0; i<4; i++) {
            runnableStations[i] = new Thread(new StationTask(i+1, 25), "RunnableStation-" + (i+1));
            runnableStations[i].start();
        }
        for (Thread t : runnableStations) t.join();
        System.out.println("Runnable total: " + (4*25));
    }
}
