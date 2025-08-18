package com.hunko.algostack.member.domain.mapper;

import com.hunko.algostack.member.domain.entity.Member;
import com.hunko.algostack.member.domain.vo.MemberInfo;

public class MemberMapper {

    public static MemberInfo toMemberInfo(Member member) {
        return new MemberInfo(member.getEmailValue(), member.getNickname());
    }
}
