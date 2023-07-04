package com.sparta.wish.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //FK

    private String username; //있어야 되는지 의문임? 확인필요

    private String title;

    private String content;

    public Board() {
    }

    public Board(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }
}
