package com.sparta.wish.controller;

import com.sparta.wish.dto.BoardRequestDto;
import com.sparta.wish.dto.CommentRequestDto;
import com.sparta.wish.dto.UserRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moc")
public class MockController {
    //프론트 테스트용 임시 컨트롤러 입니다


    //createComment 함수가 작동이안되서 확인 불가
    @PostMapping("/challenges/{postId}/replys")
    public String createComment(CommentRequestDto requestDto, @PathVariable String postId){
        return requestDto.toString() + "\n postId : "+postId;
    }

    //게시글 등록 요청 이상 무
    @PostMapping("/new-challenge")
    public String newChallenge(BoardRequestDto requestDto){
        return requestDto.toString();
    }

    //회원가입 요청 이상 무
    @PostMapping("/users/new-user")
    public String creataeUser(UserRequestDto requestDto){
        return requestDto.toString();
    }



}
