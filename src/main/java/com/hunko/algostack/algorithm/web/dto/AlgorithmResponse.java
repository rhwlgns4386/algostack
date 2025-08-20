package com.hunko.algostack.algorithm.web.dto;

import com.hunko.algostack.algorithm.domain.entity.Platform;
import com.hunko.algostack.algorithm.domain.entity.Result;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AlgorithmResponse(
        Long id,
        String title,
        Platform platform,
        Result result,
        String url,
        LocalDateTime solvedAt
) {
}
