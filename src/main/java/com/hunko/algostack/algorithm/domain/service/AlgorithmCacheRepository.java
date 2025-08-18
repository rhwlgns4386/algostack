package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmCache;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmId;
import com.hunko.algostack.algorithm.domain.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlgorithmCacheRepository extends JpaRepository<AlgorithmCache, Long> {
    Optional<AlgorithmCache> findByUserIdAndAlgorithmIdAndSolvedDate(UserId userId, AlgorithmId algorithmId, LocalDate solvedDate);

    @Query("""
            select a
            from AlgorithmCache a
            where a.userId = :userId
            and a.solvedDate between :startDate and :endDate
            """)
    List<AlgorithmCache> findByUserIdAndRange(UserId userId, LocalDate startDate, LocalDate endDate);
}
