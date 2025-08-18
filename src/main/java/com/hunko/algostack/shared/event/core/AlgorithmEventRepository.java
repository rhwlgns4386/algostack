package com.hunko.algostack.shared.event.core;

import com.hunko.algostack.shared.event.core.entity.AlgorithmEvent;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmEventRepository extends JpaRepository<AlgorithmEvent, Long> {

    List<AlgorithmEvent> findByIsSuccessIsFalse(Limit attr1);
}
