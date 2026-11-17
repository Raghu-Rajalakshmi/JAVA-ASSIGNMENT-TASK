package parking.model;

import java.time.LocalDateTime;

public final class ParkingTicket {
    private static long nextId = 1;
    private final long ticketNo;
    private final Vehicle vehicle;
    private final int floor;
    private final int slot;
    private final LocalDateTime entryTime;

    public ParkingTicket(Vehicle vehicle, int floor, int slot) {
        this(vehicle, floor, slot, LocalDateTime.now());
    }
    public ParkingTicket(Vehicle vehicle, int floor, int slot, LocalDateTime entryTime) {
        if (vehicle == null || floor < 0 || slot < 0 || entryTime == null)
            throw new IllegalArgumentException("Invalid ticket data");
        this.ticketNo = nextId++;
        this.vehicle = vehicle;
        this.floor = floor;
        this.slot = slot;
        this.entryTime = entryTime;
    }
    public long getTicketNo() { return ticketNo; }
    public Vehicle getVehicle() { return vehicle; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public LocalDateTime getEntryTime() { return entryTime; }
}
