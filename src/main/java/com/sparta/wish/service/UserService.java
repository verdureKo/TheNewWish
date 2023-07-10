package com.sparta.wish.service;

import com.sparta.wish.dto.User.SignupRequestDto;
import com.sparta.wish.dto.User.UserProfileRequestDto;
import com.sparta.wish.dto.User.UserProfileResponseDto;
import com.sparta.wish.entity.User;
import com.sparta.wish.jwtUtil.JwtUtil;
import com.sparta.wish.repository.UserRepository;
import com.sparta.wish.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
    public UserProfileResponseDto update(UserDetailsImpl userDetails, UserProfileRequestDto requestDto){
        User user = userDetails.getUser();
        //user.getPassword해서 나온 비밀번호랑 requestDto.getCheckpassword()랑 비교하는것 맞나요?
        //맞으면

        if(passwordEncoder.matches(requestDto.getCheckpassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestDto.getUpdatepassword()));
            user.setIntroduction(requestDto.getIntroduction());
            user.setImage(requestDto.getImage());
            userRepository.save(user);

            UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(user);
            return userProfileResponseDto;
        } else {
            throw new IllegalArgumentException("비밀번호가 틀립니다");
        }

    }
}
