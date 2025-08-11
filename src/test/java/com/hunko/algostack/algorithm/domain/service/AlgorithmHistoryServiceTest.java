package com.hunko.algostack.algorithm.domain.service;

import com.hunko.algostack.algorithm.domain.vo.AlgorithmHistorySaveCommand;
import com.hunko.algostack.algorithm.domain.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlgorithmHistoryServiceTest {

    @Mock
    private AlgorithmHistoryRepository algorithmHistoryRepository;

    @Test
    void 알고리즘_목록_저장() {
        AlgorithmHistoryService algorithmHistoryService = new AlgorithmHistoryService(algorithmHistoryRepository);
        Long userId = 1L;
        AlgorithmHistorySaveCommand algorithmHistorySaveCommand = AlgorithmHistorySaveCommand.builder()
                .platformAlgorithmId(1L)
                .userId(userId).title("Two Sum")
                .platform(Platform.LEETCODE)
                .result(Result.SUCCESS)
                .url("https://leetcode.com/problems/two-sum/description")
                .build();

        Assertions.assertThatNoException().isThrownBy(() -> {
            algorithmHistoryService.save(algorithmHistorySaveCommand);
        });
    }

}
