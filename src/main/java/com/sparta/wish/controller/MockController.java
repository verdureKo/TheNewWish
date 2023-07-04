package com.sparta.wish.controller;

import com.sparta.wish.dto.BoardRequestDto;
import com.sparta.wish.dto.CommentRequestDto;
import com.sparta.wish.dto.UserRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moc")
public class MockController {
    //프론트 테스트용 임시 컨트롤러 입니다

    //댓글 등록 요청 이상 무
    @PostMapping("/challenges/{userId}/replys/{postId}")
    public String createComment(CommentRequestDto requestDto, @PathVariable Long postId, @PathVariable Long userId){
        return requestDto.toString() + "\n postId : "+postId+"\n userId : "+userId;
    }
    //게시글 수정 요청 이상 무
    @PutMapping("/challenges/{userId}/{postId}")
    public String updateBoard(@PathVariable Long userId, @PathVariable Long postId, BoardRequestDto requestDto){
        return requestDto.toString()+"\nuserId : " +userId+"\npostId : "+postId;
    }





    //게시글 등록 요청 이상 무
    @PostMapping("/new-challenge")
    public String newChallenge(BoardRequestDto requestDto){
        return requestDto.toString();
    }



    //회원 가입 요청 이상 무
    @PostMapping("/users/new-user")
    public String creataeUser(UserRequestDto requestDto){
        return requestDto.toString();
    }

    //회원 수정 요청 이상 무
    @PutMapping("/users/{userId}")
    public String updateUser(UserRequestDto requestDto, @PathVariable Long userId){
        return requestDto.toString()+"\nuserId : "+userId;
    }


}
