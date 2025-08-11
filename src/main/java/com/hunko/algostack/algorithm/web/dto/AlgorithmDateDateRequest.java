package com.hunko.algostack.algorithm.web.dto;

import com.hunko.algostack.algorithm.domain.vo.DateRange;
import com.hunko.algostack.algorithm.web.factory.DateRangeFactory;

public record AlgorithmDateDateRequest(
        Integer year,
        Integer month,
        Integer day
) {
    public DateRange toDateRange() {
        return DateRangeFactory.createDateRange(year, month, day);
    }
}
