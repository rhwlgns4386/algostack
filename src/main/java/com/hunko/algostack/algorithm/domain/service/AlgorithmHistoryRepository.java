package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmId;
import com.hunko.algostack.algorithm.domain.entity.UserId;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlgorithmHistoryRepository extends JpaRepository<AlgorithmHistory, Long> {
    List<AlgorithmHistory> findAllByUserIdAndAlgorithmIdOrderBySolvedAtDesc(UserId userId, AlgorithmId algorithmId);

    List<AlgorithmHistory> findByCreatedAtAfter(LocalDateTime lastedUpdateDate, Limit limit);
}
