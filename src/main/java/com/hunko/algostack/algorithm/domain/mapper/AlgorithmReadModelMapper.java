package com.hunko.algostack.algorithm.domain.mapper;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmCache;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.vo.AlgorithmReadModel;

public class AlgorithmReadModelMapper {



    public static AlgorithmReadModel toModel(AlgorithmCache algorithmCache) {
        return new AlgorithmReadModel(
                algorithmCache.getPlatform(),
                algorithmCache.getPlatformAlgorithmId(),
                algorithmCache.getTitle(),
                algorithmCache.getResult(),
                algorithmCache.getUrl(),
                algorithmCache.getLastSolvedAt()
        );
    }

    public static AlgorithmReadModel toModel(AlgorithmHistory history) {
        return new AlgorithmReadModel(
                history.getPlatform(),
                history.getPlatformAlgorithmId(),
                history.getTitle(),
                history.getResult(),
                history.getUrl(),
                history.getSolvedAt()
        );
    }
}
