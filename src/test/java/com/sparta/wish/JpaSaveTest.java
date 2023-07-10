package com.sparta.wish;

import com.sparta.wish.entity.Board;
import com.sparta.wish.entity.Reply;
import com.sparta.wish.entity.User;
import com.sparta.wish.repository.BoardRepository;
import com.sparta.wish.repository.ReplyRepository;
import com.sparta.wish.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@SpringBootTest
public class JpaSaveTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ReplyRepository replyRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("더미 데이터 삽입")
    void insertUsers() {
        List<User> userList = new ArrayList<>();

        User user1 = new User("yesung", "1234", "안녕하세요 김예성입니다. 잘 부탁드립니다. 챌린지 웹사이트 이용해조 많이 이용해조!", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfHv0HVlndqwOD6P6vXPE2P7OibzDYavCerALlPIZkFHn21juZCV5J7RGLFvtv3GhLt9c&usqp=CAU");
        userList.add(user1);
        User user2 = new User("junyoung", "1234", "안녕하세요 김준영입니다. 잘 부탁드립니다. 챌린지 웹사이트 이용해조 많이 이용해조!", "https://img.danawa.com/prod_img/500000/147/615/img/14615147_1.jpg?_v=20220426173016");
        userList.add(user2);
        User user3 = new User("jangwon", "1234", "안녕하세요 김장원입니다. 잘 부탁드립니다. 챌린지 웹사이트 이용해조 많이 이용해조!", "https://item.kakaocdn.net/do/d97f27efd1d10d84215842e2e12752938b566dca82634c93f811198148a26065");
        userList.add(user3);
        User user4 = new User("donggyu", "1234", "안녕하세요 김동규입니다. 잘 부탁드립니다. 챌린지 웹사이트 이용해조 많이 이용해조!", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyZ2aIlKyvXrO-PyLBAISO8vSradzzCcUcjQ&usqp=CAU");
        userList.add(user4);
        User user5 = new User("pureum", "1234", "안녕하세요 고푸름입니다. 잘 부탁드립니다. 챌린지 웹사이트 이용해조 많이 이용해조!", "https://t1.daumcdn.net/cfile/tistory/992FA9345AB64A0D19");
        userList.add(user5);
        userRepository.saveAll(userList);

        List<Board> boardList = new ArrayList<>();
        Board board1 = new Board(user1, "미친듯이 잠자기", "12시간 동안 깨지않고 잠자겠슴다!", 12, 0);
        Board board2 = new Board(user2, "스프링 시큐리티 정복", "3일 이내에 스프링 시큐리티를 완강하겠습니다!", 72, 0);
        Board board3 = new Board(user3, "간식을 끊기", "한 달 동안 간식을 끊겠습니다!", 720, 0);
        Board board4 = new Board(user4, "연관관계 정복", "2일 동안 연관관계를 정복하겠습니다!", 48, 0);
        Board board5 = new Board(user5, "폴라포 10개 먹기", "하루동안 폴라포 10개를 먹겠습니다!", 24, 0);
        boardList.add(board1);
        boardList.add(board2);
        boardList.add(board3);
        boardList.add(board4);
        boardList.add(board5);
        boardRepository.saveAll(boardList);


        List<Reply> replyList = new ArrayList<>();
        Reply reply1 = new Reply(user1, board1, "올ㅋ응원합니다.");
        Reply reply6 = new Reply(user1, board1, "화이팅 😁");
        Reply reply7 = new Reply(user2, board1, "👏👏👏👏👏화이팅 ");
        Reply reply2 = new Reply(user2, board2, "도전 꼭 성공하시기를.. ");
        Reply reply3 = new Reply(user3, board2, "응원합니다 🙉");
        Reply reply4 = new Reply(user3, board3, "😳 화이팅!");
        Reply reply5 = new Reply(user4, board3, "핳흫핳흫흫");
        replyList.add(reply1);
        replyList.add(reply2);
        replyList.add(reply3);
        replyList.add(reply4);
        replyList.add(reply5);
        replyList.add(reply6);
        replyList.add(reply7);
        replyRepository.saveAll(replyList);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("데이터 찾는 방법")
    void findData () {
        Optional<Board> findBoard = boardRepository.findById(1L);
        Board board = findBoard.get();
        User user = board.getUser();
        System.out.println(user);
    }
}
