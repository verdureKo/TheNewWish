package com.sparta.wish.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.wish.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    private String title;

    private String content;

    // 서비스에서 숫자로 보내줌 예) 12
    // 서비스에서 숫자를 시간으로 바꿔서
    // 원래 시간에 더한다.
    // 최종적으로 현재 시간에 더한다.
    private Integer timer;

    //챌린지 상태 - 성공 실패(0:진행중, 1:성공, 2:실패)
    private Integer state;

    // 조회만 되고 저장은 안 됩니다.
    @OneToMany(mappedBy = "board")
    @JsonBackReference
    private List<Reply> replyList = new ArrayList<>();

    public Board() {
    }

    public Board(User user, String title, String content, Integer timer, Integer state) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.timer = timer;
        this.state = state;
    }

    public Board(User user, BoardRequestDto requestDto) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.timer = requestDto.getTimer();
        this.content = requestDto.getContent();
        this.state = 0;
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.timer = requestDto.getTimer();
    }

    public void updateOnlyState(int state) {
        this.state = state;
    }
}
