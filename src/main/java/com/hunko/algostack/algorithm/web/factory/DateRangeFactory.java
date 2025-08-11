package com.hunko.algostack.algorithm.web.factory;

import com.hunko.algostack.algorithm.domain.vo.DateRange;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class DateRangeFactory {

    public DateRange createDateRange(Integer year, Integer month, Integer day) {
        if (day == null) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().maxLength());
            return new DateRange(startDate, endDate);
        }
        LocalDate date = LocalDate.of(year, month, day);
        return new DateRange(date, date);
    }
}
