package com.sparta.wish.entity;

import com.sparta.wish.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String reply;

    public Reply() {
    }

    public Reply(User user, ReplyRequestDto replyRequestDto, Board board) {
        this.user = user;
        this.reply = replyRequestDto.getReply();
        this.board = board;
    }

    public void update(ReplyRequestDto replyRequestDto) {
        this.reply = replyRequestDto.getReply();
    }

    public Reply(User user, Board board, String reply) {
        this.user = user;
        this.board = board;
        this.reply = reply;
    }
}
