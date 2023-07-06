package com.sparta.wish.controller;

import com.sparta.wish.dto.ReplyResponseDto;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.service.ReplyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/a")
public class ReplyController {

    ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    //Reply 전체 조회
    @GetMapping
    public List<ReplyResponseDto> a() {
        return replyService.findOne();
    }
}
