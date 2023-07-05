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


@Service
@RequiredArgsConstructor
public class ReplyService {

    public final ReplyRepository replyRepsitory;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // Reply 작성
    @Transactional
    public ReplyResponseDto createReply(Long boardId, ReplyRequestDto replyRequestDto, HttpServletRequest httpServletRequest) {
        User user = checkToken(httpServletRequest);

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Reply reply = new Reply(user, replyRequestDto, board);
        replyRepsitory.save(reply);
        return new ReplyResponseDto(reply);
    }

    // Reply 수정
    @Transactional
    public ReplyResponseDto updateReply(Long replyId, ReplyRequestDto replyRequestDto, HttpServletRequest httpServletRequest) {
        User user = checkToken(httpServletRequest);

        Reply reply = replyRepsitory.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        if (reply.getUser().getUsername().equals(user.getUsername())) {
            reply.update(replyRequestDto);
            return new ReplyResponseDto(reply);
        } else {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }
    }

    // Reply 삭제
    @Transactional
    public ApiResult deleteReply(Long replyId, HttpServletRequest httpServletRequest) {
        User user = checkToken(httpServletRequest);

        Reply reply = replyRepsitory.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
        );

        if (reply.getUser().getUsername().equals(user.getUsername())) {
            replyRepsitory.delete(reply);
            return new ApiResult("삭제 성공", 200);
        } else {
            return new ApiResult("삭제 실패", 400);
        }
    }


    // Token 체크
    public User checkToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                //토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            //토큰에서 가져온 사용자 정보를 사용하여 DB조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return user;
        }
        return null;
    }
}
