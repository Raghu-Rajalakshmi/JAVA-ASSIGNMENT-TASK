package parking.service;

import java.time.LocalDate;

public final class MonthlyPass implements Pass {
    private final LocalDate validFrom, validUntil;
    public MonthlyPass(LocalDate validFrom, LocalDate validUntil) {
        if (validFrom == null || validUntil == null || validUntil.isBefore(validFrom))
            throw new IllegalArgumentException("Invalid pass period");
        this.validFrom = validFrom; this.validUntil = validUntil;
    }
    @Override public boolean isValidOn(LocalDate date) {
        return !date.isBefore(validFrom) && !date.isAfter(validUntil);
    }
}
