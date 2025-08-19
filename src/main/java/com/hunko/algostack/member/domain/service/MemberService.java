package com.hunko.algostack.member.domain.service;

import com.hunko.algostack.member.domain.entity.Email;
import com.hunko.algostack.member.domain.entity.Member;
import com.hunko.algostack.member.domain.vo.MemberInfo;
import com.hunko.algostack.member.domain.vo.SingInCommand;
import com.hunko.algostack.member.exception.LoginException;
import com.hunko.algostack.member.exception.SingInException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.hunko.algostack.member.domain.mapper.MemberMapper.toMemberInfo;

@Component
@RequiredArgsConstructor
public class MemberService {

    public final MemberRepository memberRepository;

    public MemberInfo login(Email email,String password) throws LoginException {
        Member member = memberRepository.findByEmail(email).orElseThrow(LoginException::new);
        if (!member.isSamePassword(password)) {
            throw new LoginException();
        }
        return toMemberInfo(member);
    }

    public MemberInfo getMemberFrom(Email email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(LoginException::new);
        return toMemberInfo(member);
    }

    public void signIn(SingInCommand command){
        Optional<Member> byEmail = memberRepository.findByEmail(command.emailObject());
        if (byEmail.isPresent()) {
            throw new SingInException();
        }
        memberRepository.save(command.toEntity());
    }
}
