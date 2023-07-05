package com.sparta.wish.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyResponseDto {
    private String reply;

    public ReplyResponseDto(String reply) {
        this.reply = reply;
    }
}
