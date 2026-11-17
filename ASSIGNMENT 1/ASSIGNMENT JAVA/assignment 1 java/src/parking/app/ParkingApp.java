package parking.app;

import parking.model.*;
import parking.service.*;
import java.time.*;

public class ParkingApp {
    public static void main(String[] args) {
        ParkingFacility facility = new ParkingFacility(new int[]{2, 3, 1, 4, 2}, new DefaultPricingRule());
        LocalDateTime entry = LocalDateTime.of(2026, 11, 17, 10, 0);

        ParkingTicket bike = facility.enter(new TwoWheeler("TN01A1111"));
        ParkingTicket car = facility.enter(new Car("TN02B2222"));
        ParkingTicket ev = facility.enter(new ElectricCar("TN03C3333"));
        ParkingTicket ebike = facility.enter(new EBike("TN04D4444"));
        ParkingTicket van = facility.enter(new DeliveryVan("TN05E5555"));

        System.out.println("Allocated tickets: " + facility.getActiveTicketCount());
        System.out.printf("Two-wheeler fee (61 min): Rs %.2f%n",
                facility.exit("TN01A1111", entry.plusMinutes(61), null, 0));
        System.out.printf("Car monthly-pass fee (60 min): Rs %.2f%n",
                facility.exit("TN02B2222", entry.plusMinutes(60), new MonthlyPass(entry.toLocalDate().minusDays(2), entry.toLocalDate().plusDays(28)), 0));
        System.out.printf("EV fee incl. 10 kWh (61 min): Rs %.2f%n",
                facility.exit("TN03C3333", entry.plusMinutes(61), null, 10));
        System.out.printf("E-bike flat fee (1 min): Rs %.2f%n",
                facility.exit("TN04D4444", entry.plusMinutes(1), null, 0));
        System.out.printf("Delivery van fee (2 hours from 10:00): Rs %.2f%n",
                facility.exit("TN05E5555", entry.plusHours(2), null, 0));
        System.out.println("Remaining occupancy: " + facility.getOccupancy());
    }
}
