package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.hunko.algostack.algorithm.domain.mapper.AlgorithmMapper.toAlgorithmCache;

@Service
@RequiredArgsConstructor
public class AlgorithmCacheService {

    private final AlgorithmCacheRepository algorithmCacheRepository;

    public void updateCache(List<AlgorithmHistory> algorithmHistories) {
        for (AlgorithmHistory algorithmHistory : algorithmHistories) {
            Optional<AlgorithmCache> algorithmCacheOptional = algorithmCacheRepository.findByUserIdAndAlgorithmIdAndSolvedDate(algorithmHistory.getUserId(),
                    algorithmHistory.getAlgorithmId()
            ,algorithmHistory.getCreatedAt().toLocalDate());
            if (algorithmCacheOptional.isPresent()) {
                AlgorithmCache algorithmCache = algorithmCacheOptional.get();
                algorithmCache.updateResult(algorithmHistory.getResult());
                continue;
            }
            AlgorithmCache algorithmCache = toAlgorithmCache(algorithmHistory);
            algorithmCacheRepository.save(algorithmCache);
        }
    }
}
