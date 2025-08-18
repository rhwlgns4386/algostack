package com.hunko.algostack.member.domain.vo;

import com.hunko.algostack.member.domain.entity.Email;
import com.hunko.algostack.member.domain.entity.Member;
import com.hunko.algostack.member.domain.entity.Password;

public record SingInCommand(String email, String password, String nickname) {

    public Member toEntity(){
        Email emailObject = emailObject();
        Password passwordObject = Password.from(password);
        return new Member(emailObject, passwordObject, nickname);
    }

    public Email emailObject(){
        return new Email(email);
    }
}
