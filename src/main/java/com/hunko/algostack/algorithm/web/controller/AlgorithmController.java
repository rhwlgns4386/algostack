package com.hunko.algostack.algorithm.web.controller;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmId;
import com.hunko.algostack.algorithm.domain.entity.Platform;
import com.hunko.algostack.algorithm.domain.service.AlgorithmHistoryService;
import com.hunko.algostack.algorithm.domain.vo.AlgorithmReadModel;
import com.hunko.algostack.algorithm.domain.service.AlgorithmReadService;
import com.hunko.algostack.algorithm.util.SecurityUtil;
import com.hunko.algostack.algorithm.web.dto.AlgorithmCreateRequest;
import com.hunko.algostack.algorithm.web.dto.AlgorithmDateDateRequest;
import com.hunko.algostack.algorithm.web.dto.AlgorithmsResponse;
import com.hunko.algostack.algorithm.web.mapper.AlgorithmResponseMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/algorithm")
@RequiredArgsConstructor
public class AlgorithmController {

    private final AlgorithmHistoryService algorithmHistoryService;
    private final AlgorithmReadService readService;

    @GetMapping
    Map<String,AlgorithmsResponse> getAlgorithmHistory(AlgorithmDateDateRequest request) {
        List<AlgorithmReadModel> algorithmHistories = readService.getAlgorithmFrom(SecurityUtil.getUserId(), request.toDateRange());
        return AlgorithmResponseMapper.toMonthAlgorithmsResponse(algorithmHistories);
    }

    @GetMapping("/{platform}/{id}")
    AlgorithmsResponse getAlgorithmHistory(@Validated @NotNull @PathVariable("platform") Platform platform, @NotNull @PathVariable("id") Long id) {
        List<AlgorithmReadModel> algorithmHistories = readService.getListFrom(SecurityUtil.getUserId(), AlgorithmId.of(platform, id));
        return AlgorithmResponseMapper.toAlgorithmsResponse(algorithmHistories);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createAlgorithm(@RequestBody @Validated AlgorithmCreateRequest createRequest) {
        algorithmHistoryService.save(createRequest.toCommand(SecurityUtil.getUserId()));
    }
}
