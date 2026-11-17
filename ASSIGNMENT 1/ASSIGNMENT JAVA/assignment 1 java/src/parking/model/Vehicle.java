package parking.model;

public abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("Registration number is required");
        this.regNo = regNo.trim().toUpperCase();
    }
    public final String getRegNo() { return regNo; }
    public abstract double ratePerHour();

    public double calculateFee(int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        int hours = (minutes + 59) / 60; // ceiling division; 0 minutes costs 0
        return hours * ratePerHour();
    }
    @Override public boolean equals(Object obj) {
        return obj instanceof Vehicle other && regNo.equals(other.regNo);
    }
    @Override public int hashCode() { return regNo.hashCode(); }
    @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
}
