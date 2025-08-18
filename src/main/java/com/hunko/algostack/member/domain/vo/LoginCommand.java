package com.hunko.algostack.member.domain.vo;

import com.hunko.algostack.member.domain.entity.Email;

public record LoginCommand(Email email, String password) {

    public String emailValue(){
        return this.email.getEmail();
    }
}
