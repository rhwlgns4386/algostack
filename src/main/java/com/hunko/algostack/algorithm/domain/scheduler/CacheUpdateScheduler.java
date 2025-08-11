package com.hunko.algostack.algorithm.domain.scheduler;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.service.AlgorithmCacheService;
import com.hunko.algostack.algorithm.domain.service.AlgorithmHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CacheUpdateScheduler {

    private final LastedUpdateStore lastedUpdateStore;
    private final AlgorithmHistoryRepository algorithmHistoryRepository;
    private final AlgorithmCacheService algorithmCacheService;
    private final Limit size = Limit.of(100);

    @Transactional
    @Scheduled(fixedRate = 60000 * 5)
    public void updateCache() {
        List<AlgorithmHistory> algorithmHistories = algorithmHistoryRepository.findByCreatedAtAfter(lastedUpdateStore.getLastedUpdate(), size);
        if (algorithmHistories.isEmpty()) {
            return;
        }
        algorithmCacheService.updateCache(algorithmHistories);
        lastedUpdateStore.updateLastedDate(algorithmHistories.getLast().getCreatedAt());
    }
}
