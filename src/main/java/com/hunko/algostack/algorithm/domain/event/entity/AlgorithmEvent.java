package com.hunko.algostack.algorithm.domain.event.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hunko.algostack.algorithm.domain.entity.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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
    @Type(JsonType.class)
    private String data;

    public AlgorithmEvent(String eventName, String data) {
        this.eventName = eventName;
        this.data = data;
    }

    public void success() {
        this.isSuccess = true;
    }
}
