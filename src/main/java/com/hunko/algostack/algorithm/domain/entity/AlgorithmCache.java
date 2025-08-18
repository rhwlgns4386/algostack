package com.hunko.algostack.algorithm.domain.entity;

import com.hunko.algostack.shared.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Table(indexes = @Index(name = "idx_algorithm_user_id_solved_date", columnList = "user_id, solved_date"))
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgorithmCache extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserId userId;

    @Embedded
    private AlgorithmId algorithmId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Result result;

    private String url;

    private LocalDate solvedDate;

    public AlgorithmCache(UserId userId, AlgorithmId algorithmId, String title, Result result, String url, LocalDate solvedDate) {
        this.userId = userId;
        this.algorithmId = algorithmId;
        this.title = title;
        this.result = result;
        this.url = url;
        this.solvedDate = solvedDate;
    }

    public void updateResult(Result result) {
        if (this.result.isSuccess()) {
            return;
        }
        this.result = result;
    }

    public Long getPlatformAlgorithmId() {
        return algorithmId.getPlatformAlgorithmId();
    }

    public Platform getPlatform() {
        return algorithmId.getPlatform();
    }
}
