package com.hunko.algostack.algorithm.domain.vo;

import com.hunko.algostack.algorithm.domain.entity.*;
import lombok.Builder;

@Builder
public record AlgorithmHistorySaveCommand(UserId userId, Long platformAlgorithmId, String title, Platform platform,
                                          Result result, String url) {
    public AlgorithmHistory toEntity() {
        AlgorithmId algorithmId = AlgorithmId.of(platform,platformAlgorithmId);
        return new AlgorithmHistory(userId, algorithmId, title, result, url);
    }
}
