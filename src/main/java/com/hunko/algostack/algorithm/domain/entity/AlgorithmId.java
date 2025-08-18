package com.hunko.algostack.algorithm.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class AlgorithmId {

    @Enumerated(EnumType.STRING)
    private Platform platform;
    private Long platformAlgorithmId;

    public static AlgorithmId of(Platform platform, Long platformAlgorithmId) {
        return new AlgorithmId(platform, platformAlgorithmId);
    }
}
