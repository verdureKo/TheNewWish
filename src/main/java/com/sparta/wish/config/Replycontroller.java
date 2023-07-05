package com.sparta.wish.config;

import com.sparta.wish.dto.ApiResult;
import com.sparta.wish.dto.ReplyRequestDto;
import com.sparta.wish.dto.ReplyResponseDto;
import com.sparta.wish.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Replycontroller {

    private final ReplyService replyService;

    //Reply 작성 api
    @PostMapping("/challenges/{replyId}")
    public ReplyResponseDto createReply (@PathVariable Long boardId, @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest httpServletRequest) {
        return replyService.createReply(boardId, replyRequestDto, httpServletRequest);
    }

    //Reply 수정 API
    @PutMapping("/challenges/{replyId}")
    public ReplyResponseDto updateReply(@PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest httpServletRequest) {
        return replyService.updateReply(replyId, replyRequestDto, httpServletRequest);
    }

    // Reply 삭제 API
    @DeleteMapping("challenges/{replyId}")
    public ApiResult deleteReply(@PathVariable Long replyId, HttpServletRequest httpServletRequest) {
        return replyService.deleteReply(replyId, httpServletRequest);
    }
}
