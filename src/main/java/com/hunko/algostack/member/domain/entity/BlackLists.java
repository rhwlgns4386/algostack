package com.hunko.algostack.member.domain.entity;

import com.hunko.algostack.shared.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(name = "idx_jti",columnList = "jti")})
public class BlackLists extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jti;

    public BlackLists(String jti) {
        this.jti = jti;
    }
}
