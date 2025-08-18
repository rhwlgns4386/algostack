package com.hunko.algostack.member.domain.service;

import com.hunko.algostack.member.domain.entity.Member;
import com.hunko.algostack.member.domain.vo.LoginCommand;
import com.hunko.algostack.member.domain.vo.MemberInfo;
import com.hunko.algostack.member.domain.vo.SingInCommand;
import com.hunko.algostack.member.exception.LoginException;
import com.hunko.algostack.member.exception.SingInException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.hunko.algostack.member.domain.mapper.MemberMapper.toMemberInfo;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public MemberInfo login(LoginCommand loginCommand) {
        Member member = memberRepository.findByEmail(loginCommand.email()).orElseThrow(LoginException::new);
        if (!member.isSamePassword(loginCommand.password())) {
            throw new LoginException();
        }
        return toMemberInfo(member);
    }

    public void singIn(SingInCommand command) {
        Optional<Member> byEmail = memberRepository.findByEmail(command.emailObject());
        if (byEmail.isPresent()) {
            throw new SingInException();
        }
        memberRepository.save(command.toEntity());
    }
}
