package com.sparta.wish.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @JsonManagedReference
    private Board board;

    private String reply;

    public Reply() {
    }

    public Reply(String reply) {
        this.reply = reply;
    }

    public Reply(User user, Board board, String reply) {
        this.user = user;
        this.board = board;
        this.reply = reply;
    }
}
