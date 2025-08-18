package com.hunko.algostack.algorithm.domain.entity;

import com.hunko.algostack.shared.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(name = "idx_create_at",columnList = "created_at"),@Index(name = "idx_user_id_platform_platform_id",columnList = "user_id,platform_algorithm_id,platform")})
public class AlgorithmHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AlgorithmId algorithmId;

    @Embedded
    private UserId userId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Result result;

    private String url;

    public AlgorithmHistory(UserId userId, AlgorithmId algorithmId, String title, Result result, String url) {
        this.userId = userId;
        this.algorithmId = algorithmId;
        this.title = title;
        this.result = result;
        this.url = url;
    }

    public Long getPlatformAlgorithmId() {
        return algorithmId.getPlatformAlgorithmId();
    }

    public Platform getPlatform() {
        return algorithmId.getPlatform();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AlgorithmHistory algorithmHistory = (AlgorithmHistory) o;
        return id != null && Objects.equals(id, algorithmHistory.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
