package com.sparta.wish.service;

import com.sparta.wish.dto.LoginRequestDto;
import com.sparta.wish.dto.SignupRequestDto;
import com.sparta.wish.entity.User;
import com.sparta.wish.jwtUtil.JwtUtil;
import com.sparta.wish.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    public User signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String introduction = requestDto.getIntroduction();
        String image = requestDto.getImage();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            return null;
        }

        // 사용자 등록
        User user = new User(username, password, introduction, image);
        userRepository.save(user);
        return user;
    }
}
// hi