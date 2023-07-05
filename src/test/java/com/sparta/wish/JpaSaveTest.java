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

import java.util.ArrayList;
import java.util.List;
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

    @Test
    @Transactional
    @Rollback(value = false)
    void save1() {
        User user1 = new User("se1232", "13213", "좋습니다.", "url:httfdjfd");
        userRepository.save(user1);

        Reply reply = new Reply("좋은 도전입니다.응원합니다.");
        replyRepository.save(reply);
        reply.setUser(user1);    //1번 유저가 게시글에 댓글을 단다. 1

        List<Reply> replyList = new ArrayList<>();
        replyList.add(reply);


        Board board1 = new Board(user1,"오늘의 도전1", "운동 열심히 하기1",12,1);
//        Board board2 = new Board("오늘의 도전2", "운동 열심히 하기2", 10, 2);
        board1.setUser(user1);
//        board2.setUser(user1);

        boardRepository.save(board1); //1번 유저가 1번 게시글 작성
//        boardRepository.save(board2); //1번 유저가 2번 게시글 작성


//        Reply reply = new Reply("도전 꼭 성공하시기를.. ");
//        replyRepository.save(reply);
    }

    @Test
    @Transactional
    void find1() {
        Optional<Board> findBoard = boardRepository.findById(1L);
        Board board = findBoard.get();
        User user = board.getUser();
        System.out.println(user);
    }
}
