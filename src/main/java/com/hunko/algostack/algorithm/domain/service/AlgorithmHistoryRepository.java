package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmId;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlgorithmHistoryRepository extends JpaRepository<AlgorithmHistory, Long> {
    List<AlgorithmHistory> findAllByUserIdAndAlgorithmId(Long userId, AlgorithmId algorithmId);

    List<AlgorithmHistory> findByCreatedAtAfter(LocalDateTime lastedUpdateDate, Limit limit);
}
