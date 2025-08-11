package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.vo.AlgorithmHistorySaveCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlgorithmHistoryService {

    private final AlgorithmHistoryRepository algorithmHistoryRepository;

    public void save(AlgorithmHistorySaveCommand saveCommand) {
        AlgorithmHistory algorithmHistory = saveCommand.toEntity();
        algorithmHistoryRepository.save(algorithmHistory);
    }
}
