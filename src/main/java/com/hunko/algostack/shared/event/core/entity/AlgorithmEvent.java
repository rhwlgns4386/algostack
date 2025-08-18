package com.hunko.algostack.shared.event.core.entity;

import com.hunko.algostack.shared.common.entity.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(indexes = {@Index(name = "idx_event_issuccess",columnList = "is_success")})
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
