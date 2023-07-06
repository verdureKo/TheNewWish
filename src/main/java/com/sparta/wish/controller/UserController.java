package com.sparta.wish.controller;

import com.sparta.wish.dto.User.SignupRequestDto;
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

    // 회원가입 성공 - 홈으로 이동
    // 회원가입 실패 - 그대로 (@valid 사용)
    @PostMapping("/new-user")
    public String signup(@ModelAttribute @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        log.info("requestDto={}", requestDto);
        if(bindingResult.hasErrors()) {
            return "signup";
        }

        if(userService.signup(requestDto) == null) {
            return "signup";
        }

        return "redirect:/";
    }

    //로그인 페이지 이동
    //로그인 버튼 클릭시 "/users/login/info"으로 데이터 전달
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }


    //회원 정보수정 중
    // 1. 포스트맨에서 테스트
    // 2. 그 다음 바꿔서 프론트랑 연동 예정
//    @PostMapping
//    public String update(@AuthenticationPrincipal UserDetailsImpl userDetails, SignupRequestDto requestDto) {
//        userService.update(userDetails, requestDto);
//        return
//    }

}
