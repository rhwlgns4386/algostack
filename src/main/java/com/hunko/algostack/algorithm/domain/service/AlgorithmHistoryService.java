package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.shared.event.api.AlgorithmEventPublisher;
import com.hunko.algostack.algorithm.domain.service.event.AlgorithmSolvedEvent;
import com.hunko.algostack.algorithm.domain.vo.AlgorithmHistorySaveCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlgorithmHistoryService {

    private final AlgorithmHistoryRepository algorithmHistoryRepository;
    private final AlgorithmEventPublisher eventSender;

    public void save(AlgorithmHistorySaveCommand saveCommand) {
        AlgorithmHistory algorithmHistory = saveCommand.toEntity();
        algorithmHistoryRepository.save(algorithmHistory);
        eventSender.publish(new AlgorithmSolvedEvent(algorithmHistory));
    }
}
