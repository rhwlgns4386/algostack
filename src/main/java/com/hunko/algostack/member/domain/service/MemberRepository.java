package com.hunko.algostack.member.domain.service;

import com.hunko.algostack.member.domain.entity.Email;
import com.hunko.algostack.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(Email email);
}
