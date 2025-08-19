package com.hunko.algostack.member.domain.service;

import com.hunko.algostack.member.domain.entity.Email;
import com.hunko.algostack.member.domain.entity.Member;
import com.hunko.algostack.member.domain.vo.MemberInfo;
import com.hunko.algostack.member.domain.vo.SingInCommand;
import com.hunko.algostack.member.exception.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.hunko.algostack.member.domain.mapper.MemberMapper.toMemberInfo;

@Component
@RequiredArgsConstructor
public class MemberService {

    public final MemberRepository memberRepository;

    public MemberInfo login(Email email,String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(ErrorStatus.LOGIN_FAIL::toException);
        if (!member.isSamePassword(password)) {
            ErrorStatus.LOGIN_FAIL.throwException();
        }
        return toMemberInfo(member);
    }

    public MemberInfo getMemberFrom(Email email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(ErrorStatus.LOGIN_FAIL::toException);
        return toMemberInfo(member);
    }

    public void signIn(SingInCommand command){
        Optional<Member> byEmail = memberRepository.findByEmail(command.emailObject());
        if (byEmail.isPresent()) {
            ErrorStatus.SING_IN_FAIL.throwException();
        }
        memberRepository.save(command.toEntity());
    }
}
