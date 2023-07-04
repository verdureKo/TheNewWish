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


@Slf4j
@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/users/new-user")
    public String signup(@ModelAttribute SignupRequestDto requestDto) {
        log.info("requestDto={}", requestDto);
        userService.signup(requestDto);

        return "redirect:/";
    }

    @PostMapping("challenges/users/login")
    public String login(LoginRequestDto requestDto, HttpServletResponse res) {
        log.info("requestDto={}", requestDto);
        try {
            userService.login(requestDto, res);

        } catch (Exception e) {
            return "redirect:/users/login";
        }

        return "redirect:/";
    }
}
