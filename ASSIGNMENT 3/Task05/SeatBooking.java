import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;
    SeatBooking(int seats) { booked = new boolean[seats]; }

    // Atomic check-then-book operation.
    public synchronized boolean book(int seat) {
        if (booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    public synchronized int getBookingsMade() { return bookingsMade; }

    // Deliberately unsafe version for comparison. A barrier widens the race window.
    static class UnsafeBooking {
        final boolean[] booked;
        final CyclicBarrier barrier;
        final AtomicInteger sold = new AtomicInteger();
        UnsafeBooking(int seats, int contenders) {
            booked = new boolean[seats];
            barrier = new CyclicBarrier(contenders);
        }
        void book(int seat) {
            try {
                boolean available = !booked[seat];
                barrier.await();
                if (available) { booked[seat] = true; sold.incrementAndGet(); }
            } catch (Exception e) { Thread.currentThread().interrupt(); }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];
        for (int i=0; i<users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> screen.book(seat));
            users[i].start();
        }
        for (Thread t : users) t.join();
        System.out.println("Seats sold: " + screen.getBookingsMade());

        UnsafeBooking unsafe = new UnsafeBooking(1, 10);
        Thread[] contenders = new Thread[10];
        for (int i=0; i<10; i++) { contenders[i] = new Thread(() -> unsafe.book(0)); contenders[i].start(); }
        for (Thread t : contenders) t.join();
        System.out.println("Unsafe successful attempts (demonstration): " + unsafe.sold.get());
    }
}
