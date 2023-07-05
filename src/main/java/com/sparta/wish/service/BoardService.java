package com.sparta.wish.service;

import com.sparta.wish.dto.BoardRequestDto;
import com.sparta.wish.dto.BoardResponseDto;
import com.sparta.wish.entity.Board;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.entity.User;
import com.sparta.wish.repository.BoardRepository;
import com.sparta.wish.repository.ReplyRepository;
import com.sparta.wish.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    // BEAN으로 등록된 Repository를 주입받는다.
    BoardRepository boardRepository;
    ReplyRepository replyRepository;
    BoardService(BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }



    // Board서비스 로직 시작
    public void newChallenge(User user, BoardRequestDto requestDto) {
        Board board = new Board(user, requestDto);
        boardRepository.save(board);
    }

    public List<BoardResponseDto> getChallenges() {
        LocalDateTime now = LocalDateTime.now();

        List<BoardResponseDto> responseDtoList = new ArrayList<>();
        List<Board> boardList = boardRepository.findAll();

        for (int i = 0; i < boardList.size(); i++) {
            List<Reply> replyListByBoardId = replyRepository.findAllByBoard_id(boardList.get(i).getId());
            responseDtoList.add(new BoardResponseDto(boardList.get(i), replyListByBoardId));
            if (responseDtoList.get(i).getState()==0 && !responseDtoList.get(i).getEndTime().isAfter(now)) {
                responseDtoList.get(i).setState(2);
            }
        }

        return responseDtoList;
    }

}
