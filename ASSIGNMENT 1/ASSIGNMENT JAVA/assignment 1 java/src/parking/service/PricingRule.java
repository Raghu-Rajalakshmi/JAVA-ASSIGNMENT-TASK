package parking.service;

import parking.model.Vehicle;
import java.time.LocalDateTime;

public interface PricingRule {
    double fee(Vehicle vehicle, int minutes, LocalDateTime entry, int chargingKWh);
}
