package com.hunko.algostack.algorithm.domain.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DateRange(
        LocalDate startDate, LocalDate endDate
) {
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(startDate, LocalTime.MIN);
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(endDate, LocalTime.MAX);
    }
}
