package parking.service;

import parking.model.*;
import java.time.*;
import java.util.*;

public class ParkingFacility {
    private final String[][] slots; // jagged array supports unequal floor capacities
    private final Map<String, ParkingTicket> active = new HashMap<>();
    private final PricingRule pricingRule;
    private int occupancy;

    public ParkingFacility(int[] slotsPerFloor, PricingRule pricingRule) {
        if (slotsPerFloor == null || slotsPerFloor.length == 0) throw new IllegalArgumentException("At least one floor required");
        this.slots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            if (slotsPerFloor[f] < 0) throw new IllegalArgumentException("Negative slot count");
            slots[f] = new String[slotsPerFloor[f]];
        }
        this.pricingRule = Objects.requireNonNull(pricingRule);
    }

    public int[] allocate(String vehicleNo) {
        if (vehicleNo == null || vehicleNo.isBlank()) throw new IllegalArgumentException("Vehicle number required");
        String reg = vehicleNo.trim().toUpperCase();
        if (active.containsKey(reg)) throw new IllegalArgumentException("Duplicate registration: " + reg);
        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    slots[f][s] = reg;
                    occupancy++;
                    return new int[]{f, s};
                }
            }
        }
        return null;
    }

    public ParkingTicket enter(Vehicle vehicle) {
        int[] position = allocate(vehicle.getRegNo());
        if (position == null) return null;
        ParkingTicket ticket = new ParkingTicket(vehicle, position[0], position[1]);
        active.put(vehicle.getRegNo(), ticket);
        return ticket;
    }

    public double exit(String regNo, LocalDateTime exitTime, Pass pass, int chargingKWh) {
        String reg = regNo.trim().toUpperCase();
        ParkingTicket ticket = active.get(reg);
        if (ticket == null) throw new IllegalArgumentException("No active ticket for " + reg);
        if (exitTime.isBefore(ticket.getEntryTime())) throw new IllegalArgumentException("Exit precedes entry");
        int minutes = (int) Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        double fee = (pass != null && pass.isValidOn(exitTime.toLocalDate()))
                ? 0.0 : pricingRule.fee(ticket.getVehicle(), minutes, ticket.getEntryTime(), chargingKWh);
        release(ticket.getFloor(), ticket.getSlot());
        active.remove(reg);
        return fee;
    }

    public boolean release(int floor, int slot) {
        if (floor < 0 || floor >= slots.length || slot < 0 || slot >= slots[floor].length || slots[floor][slot] == null)
            return false;
        slots[floor][slot] = null;
        occupancy--;
        return true;
    }
    public int getOccupancy() { return occupancy; }
    public int getFloorCount() { return slots.length; }
    public int getActiveTicketCount() { return active.size(); }
}
