package com.hunko.algostack.algorithm.domain.service.event;


import com.hunko.algostack.algorithm.domain.entity.AlgorithmCache;
import com.hunko.algostack.algorithm.domain.service.AlgorithmCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.hunko.algostack.algorithm.domain.mapper.AlgorithmMapper.toAlgorithmCache;

@Component
@RequiredArgsConstructor
public class AlgorithmEventHandler {

    private final AlgorithmCacheRepository algorithmCacheRepository;

    @EventListener(value = AlgorithmSolvedEvent.class)
    @Transactional
    public void handleEvent(AlgorithmSolvedEvent algorithmEvent) {
        Optional<AlgorithmCache> algorithmCacheOptional = algorithmCacheRepository.findByUserIdAndAlgorithmIdAndSolvedDate(algorithmEvent.getUserId(),
                algorithmEvent.getAlgorithmId()
                ,algorithmEvent.getSolvedDateTime().toStartDate(),
                algorithmEvent.getSolvedDateTime().toEndDate());
        if (algorithmCacheOptional.isPresent()) {
            AlgorithmCache algorithmCache = algorithmCacheOptional.get();
            algorithmCache.updateResult(algorithmEvent.getResult());
            return;
        }
        AlgorithmCache algorithmCache = toAlgorithmCache(algorithmEvent);
        algorithmCacheRepository.save(algorithmCache);
    }
}
