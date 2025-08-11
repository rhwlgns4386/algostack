package com.hunko.algostack.algorithm.domain.scheduler;

import java.time.LocalDateTime;

public interface LastedUpdateStore {
    LocalDateTime getLastedUpdate();

    void updateLastedDate(LocalDateTime createdAt);
}
