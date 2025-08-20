package com.hunko.algostack.algorithm.domain.service.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmHistory;
import com.hunko.algostack.algorithm.domain.entity.AlgorithmId;
import com.hunko.algostack.algorithm.domain.entity.Result;
import com.hunko.algostack.algorithm.domain.entity.UserId;
import com.hunko.algostack.algorithm.domain.vo.SolvedDateTime;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime solvedAt;


    public AlgorithmSolvedEvent(AlgorithmHistory algorithmHistory) {
        this.algorithmId = algorithmHistory.getAlgorithmId();
        this.userId = algorithmHistory.getUserId();
        this.title = algorithmHistory.getTitle();
        this.result = algorithmHistory.getResult();
        this.url = algorithmHistory.getUrl();
        this.solvedAt = algorithmHistory.getSolvedAt();
    }

    public SolvedDateTime getSolvedDateTime() {
        return new SolvedDateTime(solvedAt);
    }
}
