package com.sparta.wish.service;

import com.sparta.wish.dto.ReplyResponseDto;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public List<ReplyResponseDto> findOne() {
        List<Reply> all = replyRepository.findAll();
        List<ReplyResponseDto> responseDtoList = new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            Reply reply1 = all.get(i);

            responseDtoList.add(new ReplyResponseDto(reply1.getReply()));
        }

        return responseDtoList;
    }
}
