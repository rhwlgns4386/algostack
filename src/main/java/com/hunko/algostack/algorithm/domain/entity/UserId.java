package com.hunko.algostack.algorithm.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@AllArgsConstructor
public class UserId {

    private String userId;

}
