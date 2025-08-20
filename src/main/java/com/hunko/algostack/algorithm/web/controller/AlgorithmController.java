package com.hunko.algostack.algorithm.web.controller;

import com.hunko.algostack.algorithm.domain.service.AlgorithmHistoryService;
import com.hunko.algostack.algorithm.util.SecurityUtil;
import com.hunko.algostack.algorithm.web.dto.AlgorithmCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/algorithm")
@RequiredArgsConstructor
public class AlgorithmController {

    private final AlgorithmHistoryService algorithmHistoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createAlgorithm(@RequestBody @Validated AlgorithmCreateRequest createRequest) {
        algorithmHistoryService.save(createRequest.toCommand(SecurityUtil.getUserId()));
    }
}
