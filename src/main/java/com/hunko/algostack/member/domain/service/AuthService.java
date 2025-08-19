package com.hunko.algostack.member.domain.service;

import com.hunko.algostack.member.domain.entity.Email;
import com.hunko.algostack.member.domain.vo.LoginCommand;
import com.hunko.algostack.member.domain.vo.MemberInfo;
import com.hunko.algostack.member.domain.vo.SingInCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final BlackListService blackListService;

    public MemberInfo login(LoginCommand loginCommand) {
        return memberService.login(loginCommand.email(), loginCommand.password());
    }

    public MemberInfo refresh(String jti, Email email) {

        blackListService.validExpireFrom(jti);

        blackListService.addBlackList(jti);
        return memberService.getMemberFrom(email);
    }

    public void addBlackList(String jti) {
        blackListService.validExpireFrom(jti);
    }

    public void singIn(SingInCommand command) {
        memberService.signIn(command);
    }
}
