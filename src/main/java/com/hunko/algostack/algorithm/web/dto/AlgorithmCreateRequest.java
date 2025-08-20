package com.hunko.algostack.algorithm.web.dto;

import com.hunko.algostack.algorithm.domain.entity.UserId;
import com.hunko.algostack.algorithm.domain.vo.AlgorithmHistorySaveCommand;
import com.hunko.algostack.algorithm.domain.entity.Platform;
import com.hunko.algostack.algorithm.domain.entity.Result;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

public record AlgorithmCreateRequest(
        @NotNull
        Long id,
        @NotBlank
        String title,
        @NotNull
        Platform platform,
        @NotNull
        Result result,
        @URL
        String url,
        LocalDateTime solvedAt) {

    public AlgorithmHistorySaveCommand toCommand(UserId userId) {
        return AlgorithmHistorySaveCommand.builder()
                .userId(userId)
                .platformAlgorithmId(id)
                .title(title)
                .url(url)
                .platform(platform)
                .result(result)
                .solvedAt(solvedAt)
                .build();
    }
}
