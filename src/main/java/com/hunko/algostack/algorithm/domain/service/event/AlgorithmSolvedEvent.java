package com.hunko.algostack.algorithm.domain.service.event;

import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmId;
import com.hunko.algostack.algorithm.domain.entity.Result;
import com.hunko.algostack.algorithm.domain.entity.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgorithmSolvedEvent {

    private AlgorithmId algorithmId;

    private UserId userId;

    private String title;

    private Result result;

    private String url;

    private LocalDateTime createdAt;


    public AlgorithmSolvedEvent(AlgorithmHistory algorithmHistory) {
        this.algorithmId = algorithmHistory.getAlgorithmId();
        this.userId = algorithmHistory.getUserId();
        this.title = algorithmHistory.getTitle();
        this.result = algorithmHistory.getResult();
        this.url = algorithmHistory.getUrl();
        this.createdAt = algorithmHistory.getCreatedAt();
    }
}
