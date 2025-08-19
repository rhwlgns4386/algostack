package com.hunko.algostack.member.domain.service;

import com.hunko.algostack.member.domain.entity.BlackLists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackLists, Long> {

    Optional<BlackLists> findByJti(String jti);

}
