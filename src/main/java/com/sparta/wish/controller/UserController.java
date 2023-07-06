package com.sparta.wish.controller;

import com.sparta.wish.dto.User.SignupRequestDto;
import com.sparta.wish.dto.User.UserProfileRequestDto;
import com.sparta.wish.dto.User.UserProfileResponseDto;
import com.sparta.wish.security.UserDetailsImpl;
import com.sparta.wish.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지로 이동
    @GetMapping("/new-user")
    public String signupPage() {
        return "signup"; // 회원가입하면 sign.html 에서 "/users/new-user 로 이동함
    }

    // 회원정보 검증 - 성공 후
    // 회원가입 성공 - 홈으로 이동

    // 회원검증 실패 - 현재 화면
    // 회원가입 실패 - 현재 화면
    @PostMapping("/new-user")
    public String signup(@ModelAttribute @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        log.info("requestDto={}", requestDto);
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (userService.signup(requestDto) == null) {
            return "signup";
        }

        return "redirect:/";
    }

    //로그인 페이지 이동
    //로그인 버튼 클릭시 "/users/login/info"으로 데이터 전달
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    //회원 정보 조회
    @ResponseBody
    @GetMapping("/update")
    public UserProfileResponseDto profileFind(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserProfileResponseDto userProfileResponseDto = userService.profileFind(userDetails);
        return userProfileResponseDto;
    }

    //회원 정보 수정
    // post로
    // 데이터 수정 정보를 받음
    // json 으로 수정된 데이터를 보내줌.

    @PostMapping("/update")
    public void update(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid UserProfileRequestDto requestDto) {
        log.info("requestDto={}", requestDto);
        UserProfileResponseDto userProfileResponseDto = userService.update(userDetails, requestDto);

    }
}

