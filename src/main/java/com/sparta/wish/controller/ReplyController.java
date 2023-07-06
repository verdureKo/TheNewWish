package com.sparta.wish.controller;

import com.sparta.wish.dto.ApiResult;
import com.sparta.wish.dto.ReplyRequestDto;
import com.sparta.wish.dto.ReplyResponseDto;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.security.UserDetailsImpl;
import com.sparta.wish.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

//    //Reply 전체 조회 (GPT 작성)
//    @GetMapping("/challenge/replyAll")
//    public List<ReplyResponseDto> findAll() {
//        return replyService.findAll();
//    }

    //Reply 전체 조회
    @GetMapping("/challenge/replyAll")
    public List<ReplyResponseDto> findReply() {
        return replyService.findAll();
    }


    //Reply 작성 api
    @PostMapping("/challenges/{boardId}")
    public ReplyResponseDto createReply(@PathVariable Long boardId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//       log.info("댓글 작성", boardId, replyRequestDto, userDetails.getUser());
        return replyService.createReply(boardId, replyRequestDto, userDetails.getUser());
    }


//    //    Reply 수정 API
//    @PutMapping("/challenges/{boardId}")
//    public ReplyResponseDto updateReply(@PathVariable Long boardId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return replyService.updateReply(boardId, replyRequestDto, userDetails.getUser());
//    }

    // Reply 삭제 API
    @DeleteMapping("challenges/{boardId}")
    public ApiResult deleteReply(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.deleteReply(boardId, userDetails.getUser());
    }
}
