package com.sparta.wish.controller;

import com.sparta.wish.dto.BoardRequestDto;
import com.sparta.wish.dto.BoardResponseDto;
import com.sparta.wish.entity.User;
import com.sparta.wish.security.UserDetailsImpl;
import com.sparta.wish.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    // BEAN으로 등록된 BoardServie객체를 주입받는다.
    BoardService boardService;
    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/new-challenge")
    public String newChallenge(@AuthenticationPrincipal UserDetailsImpl userDetails, BoardRequestDto requestDto) {
        // Authentication 의 Principal 에 저장된 UserDetailsImpl 을 가져옵니다.
        User user =  userDetails.getUser();
        boardService.newChallenge(user,requestDto);

        return "redirect:/";
    }

    @GetMapping("/api/challenges")
    @ResponseBody
    public List<BoardResponseDto> getChallenges(){
        return boardService.getChallenges();
    }
}
