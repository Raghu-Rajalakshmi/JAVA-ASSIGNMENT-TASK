package parking.service;

import parking.model.*;
import java.time.LocalDateTime;

public class DefaultPricingRule implements PricingRule {
    @Override public double fee(Vehicle vehicle, int minutes, LocalDateTime entry, int chargingKWh) {
        double amount;
        if (vehicle instanceof DeliveryVan van) amount = van.calculateFee(minutes, entry.getHour());
        else amount = vehicle.calculateFee(minutes);
        if (vehicle instanceof Chargeable chargeable) amount += chargeable.chargingFee(chargingKWh);
        return amount;
    }
}
