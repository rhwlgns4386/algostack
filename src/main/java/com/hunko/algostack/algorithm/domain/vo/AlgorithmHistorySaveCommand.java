package com.hunko.algostack.algorithm.domain.vo;

import com.hunko.algostack.algorithm.domain.entity.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AlgorithmHistorySaveCommand(UserId userId, Long platformAlgorithmId, String title, Platform platform,
                                          Result result, String url, LocalDateTime solvedAt) {
    public AlgorithmHistory toEntity() {
        AlgorithmId algorithmId = AlgorithmId.of(platform,platformAlgorithmId);
        return new AlgorithmHistory(userId, algorithmId, title, result, url, solvedAt);
    }
}
