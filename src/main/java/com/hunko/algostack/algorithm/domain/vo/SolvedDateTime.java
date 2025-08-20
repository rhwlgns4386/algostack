package com.hunko.algostack.algorithm.domain.vo;

import com.hunko.algostack.algorithm.domain.service.event.AlgorithmSolvedEvent;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record SolvedDateTime(LocalDateTime solvedAt) {

    public LocalDateTime toStartDate() {
        return LocalDateTime.of(solvedAt.toLocalDate(), LocalTime.MIN);
    }


    public LocalDateTime toEndDate() {
        return LocalDateTime.of(solvedAt.toLocalDate(), LocalTime.MAX);
    }
}
