package com.sparta.wish.service;

import com.sparta.wish.dto.BoardRequestDto;
import com.sparta.wish.dto.BoardResponseDto;
import com.sparta.wish.entity.Board;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.entity.User;
import com.sparta.wish.repository.BoardRepository;
import com.sparta.wish.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    // BEAN으로 등록된 Repository(Board,Reply)를 주입받는다.
    BoardRepository boardRepository;
    ReplyRepository replyRepository;
    BoardService(BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }



    // Board서비스 로직 시작

    //user와 requestDto의 멤버를 필드로 가지는 Board를 생성-> Repository에 저장
    public void newChallenge(User user, BoardRequestDto requestDto) {
        Board board = new Board(user, requestDto);
        boardRepository.save(board);
    }

    //모든 BoardEntity를 RequestDto로 만듦(조금의 변환을 거친다.) -> front에 전달
    public List<BoardResponseDto> getChallenges() {
        LocalDateTime now = LocalDateTime.now();

        List<BoardResponseDto> responseDtoList = new ArrayList<>();
        List<Board> boardList = boardRepository.findAll();

        for (int i = 0; i < boardList.size(); i++) {
            List<Reply> replyListByBoardId = replyRepository.findAllByBoard_id(boardList.get(i).getId());
            responseDtoList.add(new BoardResponseDto(boardList.get(i), replyListByBoardId));
            if (responseDtoList.get(i).getState()==0 && !responseDtoList.get(i).getEndTime().isAfter(now)) {
                responseDtoList.get(i).setState(2);
            }
        }

        return responseDtoList;
    }

    // boardId를 이용해서 객체를 찾아온다 -> 로그인된 유저의 이름과 일치여부 확인 -> Entity의 필드를 변경 ->  Dirty Checking에 의해서 update자동 실행
    @Transactional
    public void updateChallenge(Long boardId, BoardRequestDto requestDto, User user) {
        Board board = findBoard(boardId);

        if (board.getUser().getUsername().equals(user.getUsername())) {
            board.update(requestDto);
        } else {
            throw new IllegalArgumentException("본인의 글만 수정할수 있습니다.");
        }
    }

    // boardId를 이용해서 객체를 찾아옴 -> 로그인된 유저의 이름과 일치여부 확인 -> Repository의 queryMethod를 사용해서 삭제
    public void deleteChallenge(Long boardId, User user) {
        Board board = findBoard(boardId);

        if (board.getUser().getUsername().equals(user.getUsername())) {
            boardRepository.delete(board);
        } else {
            throw new IllegalArgumentException("본인의 글만 삭제할수 있습니다.");
        }
    }

    // boardId와 state를 RequestParam방식으로 받아서 로그인된 유저의 이름과 일치여부 확인 -> Entity의 state를 변경 -> Dirty Checking에 의해서 update실행됨
    @Transactional
    public void updateState(Long id, int state, User user) {
        Board board = findBoard(id);



        if (board.getUser().getUsername().equals(user.getUsername())) {
            if(board.getState() == 1 || board.getState() == 2) {
                throw new IllegalArgumentException("이미 성공또는 실패 했음~");
            } else {
                board.updateOnlyState(state);
            }
        } else {
            throw new IllegalArgumentException("본인것만 가능!");
        }
    }

    // 중복되는 id로 Entity를 찾는 과정을 메서드로 정의함
    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 보드는 존재하지 않습니다.")
        );
    }

    public BoardResponseDto findChallengeById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 보드는 존재하지 않습니다."));
        List<Reply> replyListByBoardId = replyRepository.findAllByBoard_id(board.getId());

        return new BoardResponseDto(board, replyListByBoardId);
    }
}
