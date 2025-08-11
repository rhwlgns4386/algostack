package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.vo.AlgorithmReadModel;
import com.hunko.algostack.algorithm.domain.vo.DateRange;
import com.hunko.algostack.algorithm.domain.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.hunko.algostack.algorithm.domain.mapper.AlgorithmReadModelMapper.toModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlgorithmReadServiceTest {

    @Mock
    private AlgorithmHistoryRepository algorithmHistoryRepository;
    @Mock
    private AlgorithmCacheRepository algorithmCacheRepository;

    @Test
    void 알고리즘_플랫폼과_아이디로_조회() {

        Platform platform = Platform.LEETCODE;
        Long userId = 1L;
        Long platformAlgorithmId = 1L;
        AlgorithmId algorithmId = AlgorithmId.of(platform, platformAlgorithmId);
        when(algorithmHistoryRepository.findAllByUserIdAndAlgorithmId(userId, algorithmId))
                .thenReturn(List.of(
                        createDefaultAlgorithm(1L, userId, Result.SUCCESS),
                        createDefaultAlgorithm(2L, userId, Result.FAIL)
                ));
        AlgorithmReadService algorithmHistoryService = new AlgorithmReadService(algorithmCacheRepository, algorithmHistoryRepository);

        List<AlgorithmReadModel> algorithmHistories = algorithmHistoryService.getListFrom(userId, algorithmId);

        assertThat(algorithmHistories).hasSize(2)
                .contains(toModel(createDefaultAlgorithm(1L, userId, Result.SUCCESS)))
                .contains(toModel(createDefaultAlgorithm(2L, userId, Result.FAIL)));
    }

    @Test
    void 일별_푼_알고리즘_조회() {
        Long userId = 1L;

        when(algorithmCacheRepository.findByUserIdAndRange(eq(userId), any(),any()))
                .thenReturn(List.of(
                        createDefaultAlgorithmCache(1L, userId, Result.SUCCESS),
                        createDefaultAlgorithmCache(2L, userId, Result.FAIL)
                ));
        AlgorithmReadService algorithmHistoryService = new AlgorithmReadService(algorithmCacheRepository, algorithmHistoryRepository);

        List<AlgorithmReadModel> algorithmHistories = algorithmHistoryService.getAlgorithmFrom(userId, new DateRange(LocalDate.now(), LocalDate.now()));

        assertThat(algorithmHistories).hasSize(2)
                .contains(toModel(createDefaultAlgorithmCache(1L, userId, Result.SUCCESS)))
                .contains(toModel(createDefaultAlgorithmCache(2L, userId, Result.FAIL)));
    }

    private AlgorithmHistory createDefaultAlgorithm(Long id, Long userId, Result result) {
        AlgorithmHistory algorithmHistory = new AlgorithmHistory(userId,
                AlgorithmId.of(Platform.LEETCODE, 1L),
                "Two Sum",
                result,
                "https://leetcode.com/problems/two-sum/description");
        ReflectionTestUtils.setField(algorithmHistory,"createdAt", LocalDateTime.now());
        if (id != null) {
            ReflectionTestUtils.setField(algorithmHistory, "id", id);
        }
        return algorithmHistory;
    }

    private AlgorithmCache createDefaultAlgorithmCache(Long id, Long userId, Result result) {
        AlgorithmCache algorithmHistory = new AlgorithmCache(userId,
                AlgorithmId.of(Platform.LEETCODE, 1L),
                "Two Sum",
                result,
                "https://leetcode.com/problems/two-sum/description",
                LocalDate.now());
        if (id != null) {
            ReflectionTestUtils.setField(algorithmHistory, "id", id);
        }
        return algorithmHistory;
    }
}
