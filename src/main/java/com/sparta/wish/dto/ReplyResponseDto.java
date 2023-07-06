package com.sparta.wish.dto;

import com.sparta.wish.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyResponseDto {
    private Long id;
    private String reply;
    private String username;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.reply = reply.getReply();
        this.username = reply.getUser().getUsername();
    }


    public ReplyResponseDto(String reply) {
        this.reply = reply;
    }
}
