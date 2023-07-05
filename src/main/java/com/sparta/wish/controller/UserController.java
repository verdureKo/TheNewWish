package com.sparta.wish.controller;

import com.sparta.wish.dto.LoginRequestDto;
import com.sparta.wish.dto.SignupRequestDto;
import com.sparta.wish.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지로 이동
    @GetMapping("/challenges/new-user")
    public String signupPage() {
        return "signup"; // 회원가입하면 sign.html 에서 "/users/new-user 로 이동함
    }

   //  회원가입 성공 - 홈으로 이동
    // 회원가입 실패 - 그대로 (@valid 사용)
    @PostMapping("/users/new-user")
    public String signup(@ModelAttribute SignupRequestDto requestDto) {
        log.info("requestDto={}", requestDto);
        userService.signup(requestDto);

        return "redirect:/";
    }

    //로그인 페이지 이동
    //로그인 버튼 클릭시 "/challenges/users/login"으로 데이터 전달
    @GetMapping("/challenges/login")
    public String login(){
        return "login";
    }


    // 작동 안함
    @RequestMapping("/challenges/users/login")
    public String loginSuccessMain(){
        return "redirect:/";
    }
}
