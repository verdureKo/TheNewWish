package com.sparta.wish.controller;

import com.sparta.wish.dto.ApiResult;
import com.sparta.wish.dto.ReplyRequestDto;
import com.sparta.wish.dto.ReplyResponseDto;
import com.sparta.wish.security.UserDetailsImpl;
import com.sparta.wish.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    //Reply 전체 조회 (GPT 작성)
    @GetMapping("/challenge/replyAll")
    public List<ReplyResponseDto> findAll() {
        return replyService.findAll();
    }

    //Reply 작성 api
    @PostMapping("/challenges/reply/{boardId}")
    public ReplyResponseDto createReply(@PathVariable Long boardId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.createReply(boardId, replyRequestDto, userDetails.getUser());
    }


    //    Reply 수정 API
    @PutMapping("/challenges/reply/{replyId}")
    public ReplyResponseDto updateReply(@PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.updateReply(replyId, replyRequestDto, userDetails.getUser());
    }

    // Reply 삭제 API
    @DeleteMapping("challenges/reply/{replyId}")
    public ApiResult deleteReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.deleteReply(replyId, userDetails.getUser());
    }
}
