package com.sparta.wish.dto;

import com.sparta.wish.entity.Board;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardResponseDto {
    Long board_id;
    int state;
    User user;
    LocalDateTime endTime;
    LocalDateTime createAt;
    LocalDateTime modifiedAt;
    String title;
    String content;
    List<Reply> replyList;

    BoardResponseDto() {}

    public BoardResponseDto(Board board, List<Reply> replyListByBoardId) {
        this.board_id = board.getId();
        this.user = board.getUser();
        this.content = board.getContent();
        this.title = board.getTitle();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        this.endTime = board.getCreateAt().plusHours(Long.parseLong(board.getTimer() + ""));
        this.replyList = replyListByBoardId;
        this.state = board.getState();
    }
}
