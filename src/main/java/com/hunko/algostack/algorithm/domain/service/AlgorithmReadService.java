package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.entity.UserId;
import com.hunko.algostack.algorithm.domain.vo.AlgorithmReadModel;
import com.hunko.algostack.algorithm.domain.vo.DateRange;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmId;
import com.hunko.algostack.algorithm.domain.mapper.AlgorithmReadModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlgorithmReadService {
    private final AlgorithmCacheRepository algorithmCacheRepository;
    private final AlgorithmHistoryRepository algorithmHistoryRepository;

    public List<AlgorithmReadModel> getAlgorithmFrom(UserId userId, DateRange dateRange) {
        return algorithmCacheRepository.findByUserIdAndRange(userId, dateRange.startDate(), dateRange.endDate())
                .stream().map(AlgorithmReadModelMapper::toModel).toList();
    }

    public List<AlgorithmReadModel> getListFrom(UserId userId, AlgorithmId algorithmId) {
        return algorithmHistoryRepository.findAllByUserIdAndAlgorithmId(userId, algorithmId)
                .stream().map(AlgorithmReadModelMapper::toModel).toList();
    }
}
