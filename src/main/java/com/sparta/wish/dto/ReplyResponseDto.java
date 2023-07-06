package com.sparta.wish.dto;

import com.sparta.wish.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReplyResponseDto {
    private Long id;
    private String content;
    private String username;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.username = reply.getUser().getUsername();
    }
}
