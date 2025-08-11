package com.hunko.algostack.algorithm.domain.vo;

import com.hunko.algostack.algorithm.domain.entity.Platform;
import com.hunko.algostack.algorithm.domain.entity.Result;

import java.time.LocalDate;

public record AlgorithmReadModel(
        Platform platform,
        Long id,
        String title,
        Result result,
        String url,
        LocalDate solvedDate
) {
}
