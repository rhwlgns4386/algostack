package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmCache;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmId;
import com.hunko.algostack.algorithm.domain.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlgorithmCacheRepository extends JpaRepository<AlgorithmCache, Long> {
    @Query("select ac from AlgorithmCache ac where ac.userId = :userId and ac.algorithmId = :algorithmId and ac.lastSolvedAt between :startDate and :endDate")
    Optional<AlgorithmCache> findByUserIdAndAlgorithmIdAndSolvedDate(UserId userId, AlgorithmId algorithmId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
            select a
            from AlgorithmCache a
            where a.userId = :userId
            and a.lastSolvedAt between :startDate and :endDate
            """)
    List<AlgorithmCache> findByUserIdAndRange(UserId userId, LocalDateTime startDate, LocalDateTime endDate);
}
