package com.sparta.wish;

import com.sparta.wish.entity.Board;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.entity.User;
import com.sparta.wish.repository.BoardRepository;
import com.sparta.wish.repository.ReplyRepository;
import com.sparta.wish.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class JpaSaveTest {

    UserRepository userRepository;
    BoardRepository boardRepository;
    ReplyRepository replyRepository;

    @Autowired // 자동 주입 되지만, 최대한 롬북이나, 자동을 안쓰고 직접하기.
    public JpaSaveTest(UserRepository userRepository, BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }

    // 저장 테스트 할 때 사용하세요.
    @Test
    @Transactional
    @Rollback(value = false)
    void save1() {
        // 유저 저장
        User user1 = new User("유저이름1", "1", "본인 소개1", "이미지 URL1");
        User user2 = new User("유저이름2", "2", "본인 소개2", "이미지 URL2");
        userRepository.save(user1);
        userRepository.save(user2);

        // 게시글 저장
        Board board1 = new Board(user1,"오늘의 도전1", "운동 열심히 하기1",12,0);
        Board board2 = new Board(user2,"오늘의 도전2", "운동 열심히 하기2", 10, 0);

        boardRepository.save(board1); //1번 유저가 1번 게시글 작성
        boardRepository.save(board2); //1번 유저가 2번 게시글 작성

        // 댓글 저장
        Reply reply1 = new Reply(user1, board1,"좋은 도전입니다.응원합니다.");
        Reply reply2 = new Reply(user1, board2, "도전 꼭 성공하시기를.. ");
        Reply reply3 = new Reply(user1, board2, "화이팅 ");
        Reply reply4 = new Reply(user1, board1, "!!!");
        Reply reply5 = new Reply(user1, board1, "~~~");


        replyRepository.save(reply1);
        replyRepository.save(reply2);
        replyRepository.save(reply3);
        replyRepository.save(reply4);
        replyRepository.save(reply5);
    }

    // 찾는 방법 테스트 할 때 사용하세요.
    @Test
    @Transactional
    void find1() {
        Optional<Board> findBoard = boardRepository.findById(1L);
        Board board = findBoard.get();
        User user = board.getUser();
        System.out.println(user);
    }
}
