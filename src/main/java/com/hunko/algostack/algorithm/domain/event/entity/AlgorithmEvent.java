package com.hunko.algostack.algorithm.domain.event.entity;

import com.hunko.algostack.algorithm.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class AlgorithmEvent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;

    private boolean isSuccess;

    @Column(columnDefinition = "JSON")
    private String data;

    public AlgorithmEvent(String eventName, String data) {
        this.eventName = eventName;
        this.data = data;
    }

    public void success() {
        this.isSuccess = true;
    }
}
