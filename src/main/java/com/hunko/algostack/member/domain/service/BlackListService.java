package com.hunko.algostack.member.domain.service;

import com.hunko.algostack.member.domain.entity.BlackLists;
import com.hunko.algostack.member.exception.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;

    public void validExpireFrom(String jti) {
        Optional<BlackLists> blackLists = blackListRepository.findByJti(jti);
        if (blackLists.isPresent()) {
            throw new LoginException();
        }
    }

    public void addBlackList(String jti) {
        if (blackListRepository.findByJti(jti).isEmpty()) {
            blackListRepository.save(new BlackLists(jti));
        }
    }
}
