package com.hunko.algostack.algorithm.domain.vo;

import java.time.LocalDate;

public record DateRange(
        LocalDate startDate, LocalDate endDate
) {
}
