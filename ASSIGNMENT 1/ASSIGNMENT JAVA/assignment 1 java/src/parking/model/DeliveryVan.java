package parking.model;
public class DeliveryVan extends Vehicle {
    public DeliveryVan(String regNo) { super(regNo); }
    @Override public double ratePerHour() { return 30.0; }
    public double calculateFee(int minutes, int entryHour) {
        if (minutes < 0 || entryHour < 0 || entryHour > 23) throw new IllegalArgumentException("Invalid duration or hour");
        int hours = (minutes + 59) / 60;
        int premiumHours = 0;
        for (int i = 0; i < hours; i++) {
            int h = (entryHour + i) % 24;
            if (h >= 9 && h < 17) premiumHours++;
        }
        return (hours - premiumHours) * ratePerHour() + premiumHours * ratePerHour() * 2.0;
    }
}
