package com.hunko.algostack.algorithm.domain.mapper;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmCache;
import com.hunko.algostack.algorithm.domain.service.event.AlgorithmSolvedEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AlgorithmMapper {

    public static AlgorithmCache toAlgorithmCache(AlgorithmHistory algorithmHistory) {
        return new AlgorithmCache(
                algorithmHistory.getUserId(),
                algorithmHistory.getAlgorithmId(),
                algorithmHistory.getTitle(),
                algorithmHistory.getResult(),
                algorithmHistory.getUrl(),
                algorithmHistory.getCreatedAt().toLocalDate()
        );
    }

    public static AlgorithmCache toAlgorithmCache(AlgorithmSolvedEvent algorithmHistory) {
        return new AlgorithmCache(
                algorithmHistory.getUserId(),
                algorithmHistory.getAlgorithmId(),
                algorithmHistory.getTitle(),
                algorithmHistory.getResult(),
                algorithmHistory.getUrl(),
                algorithmHistory.getCreatedAt().toLocalDate()
        );
    }
}
