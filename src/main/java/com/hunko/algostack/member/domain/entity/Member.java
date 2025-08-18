package com.hunko.algostack.member.domain.entity;

import com.hunko.algostack.shared.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Embedded
    private Email email;

    @Embedded
    private Password password;

    private String nickname;

    public Member(Email email, Password password, String nickname) {
        this.email = email;
        this.password = password;
    }
}
