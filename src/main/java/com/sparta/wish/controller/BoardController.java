package com.sparta.wish.controller;

import com.sparta.wish.dto.BoardRequestDto;
import com.sparta.wish.dto.BoardResponseDto;
import com.sparta.wish.entity.User;
import com.sparta.wish.security.UserDetailsImpl;
import com.sparta.wish.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    // BEAN으로 등록된 BoardServie객체를 주입받는다.
    BoardService boardService;
    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 글 작성
    @PostMapping("/new-challenge")
    public String newChallenge(@AuthenticationPrincipal UserDetailsImpl userDetails, BoardRequestDto requestDto) {
        // Authentication 의 Principal 에 저장된 UserDetailsImpl 을 가져옵니다.
        User user =  userDetails.getUser();
        boardService.newChallenge(user,requestDto);

        return "index";
    }

    // 모든 글 조회
    @GetMapping("/api/challenges")
    @ResponseBody
    public List<BoardResponseDto> getChallenges(){
        return boardService.getChallenges();
    }

    // 글 수정
    @ResponseBody
    @PutMapping("/challenges/{boardId}")
    public void updateChallenge(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Authentication 의 Principal 에 저장된 UserDetailsImpl 을 가져옵니다.
        User user =  userDetails.getUser();
        boardService.updateChallenge(boardId, requestDto, user);
    }

    // 글 삭제
    @ResponseBody
    @DeleteMapping("/challenges/{boardId}")
    public void deleteChallenge(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Authentication 의 Principal 에 저장된 UserDetailsImpl 을 가져옵니다.
        User user =  userDetails.getUser();
        boardService.deleteChallenge(boardId, user);
    }

    // 성공, 실패 버튼 구현
    @ResponseBody
    @PutMapping("challenges/board/states")
    public void updateState(Long id, int state, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Authentication 의 Principal 에 저장된 UserDetailsImpl 을 가져옵니다.
        User user =  userDetails.getUser();
        boardService.updateState(id, state, user);
    }

    // 게시글 수정시 필요한 게시글 조회
    @GetMapping("/api/challenges/{boardId}")
    public BoardResponseDto findChallengeById(@PathVariable Long boardId) {
        return boardService.findChallengeById(boardId);
    }
}
