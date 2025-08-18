package com.hunko.algostack.algorithm.domain.event;

import com.hunko.algostack.algorithm.domain.event.entity.AlgorithmEvent;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmEventRepository extends JpaRepository<AlgorithmEvent, Long> {

    List<AlgorithmEvent> findByIsSuccessIsFalse(Limit attr1);
}
