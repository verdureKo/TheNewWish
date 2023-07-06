package com.sparta.wish.service;

import com.sparta.wish.dto.ApiResult;
import com.sparta.wish.dto.ReplyRequestDto;
import com.sparta.wish.dto.ReplyResponseDto;
import com.sparta.wish.entity.Board;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.entity.User;
import com.sparta.wish.jwtUtil.JwtUtil;
import com.sparta.wish.repository.BoardRepository;
import com.sparta.wish.repository.ReplyRepository;
import com.sparta.wish.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReplyService {

    public final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

//    //Reply 전체 조회 (GPT 작성)
//    public List<ReplyResponseDto> findAll() {
//        List<Reply> replies = replyRepository.findAll();
//        return replies.stream()
//                .map(ReplyResponseDto::new)
//                .collect(Collectors.toList());
//    }


    //Reply 전체 조회
    public List<ReplyResponseDto> findAll() {
        List<Reply> replies = replyRepository.findAll();
        List<ReplyResponseDto> responseDtoList = new ArrayList<>();

        for (int i = 0; i < replies.size(); i++) {
            Reply reply1 = replies.get(i);

            responseDtoList.add(new ReplyResponseDto(reply1.getReply()));
        }

        return responseDtoList;
    }


    // Reply 작성
    @Transactional
    public ReplyResponseDto createReply(Long boardId, ReplyRequestDto replyRequestDto, User user) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        Reply reply = new Reply(user, replyRequestDto, board);
        replyRepository.save(reply);
        return new ReplyResponseDto(reply);
    }

//    // Reply 수정
//    @Transactional
//    public ReplyResponseDto updateReply(Long replyId, ReplyRequestDto replyRequestDto, User user) {
//
//        Reply reply = replyRepository.findById(replyId).orElseThrow(
//                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
//        );
//
//        if (reply.getUser().getUsername().equals(user.getUsername())) {
//            reply.update(replyRequestDto);
//            return new ReplyResponseDto(reply);
//        } else {
//            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
//        }
//    }

    // Reply 삭제
    @Transactional
    public ApiResult deleteReply(Long replyId, User user) {

        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
        );

        if (reply.getUser().getUsername().equals(user.getUsername())) {
            replyRepository.delete(reply);
            return new ApiResult("삭제 성공", 200);
        } else {
            return new ApiResult("삭제 실패", 400);
        }
    }
}
