package com.sparta.wish.service;

import com.sparta.wish.dto.User.SignupRequestDto;
import com.sparta.wish.dto.User.UserProfileResponseDto;
import com.sparta.wish.entity.User;
import com.sparta.wish.jwtUtil.JwtUtil;
import com.sparta.wish.repository.UserRepository;
import com.sparta.wish.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    //회원 정보 조회
    public UserProfileResponseDto profileFind(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Optional<User> findUsername = userRepository.findByUsername(userDetails.getUsername());
        User user = findUsername.get();
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(user);
        return userProfileResponseDto;
    }

    //회원 정보 수정
    public UserProfileResponseDto update(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute SignupRequestDto requestDto){
        User user = userDetails.getUser();
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setIntroduction(requestDto.getIntroduction());
        user.setImage(requestDto.getImage());
        userRepository.save(user);

        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(user);
        return userProfileResponseDto;

    }
}
