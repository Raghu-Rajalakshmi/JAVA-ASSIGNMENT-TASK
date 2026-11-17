package parking.service;

import java.time.LocalDate;

public interface Pass {
    boolean isValidOn(LocalDate date);
}
