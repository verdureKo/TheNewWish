package com.sparta.wish.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    //메인 페이지 이동1
    @GetMapping("/")
    public String home1(){
        return "redirect:/challenges";
    }

    //메인 페이지 이동2
    @GetMapping("/challenges")
    public String home2(){
        return "index";
    }

    //글 작성 페이지 이동
    @GetMapping("/new-challenge")
    public String newBoard(){
        return "newboard";
    }

    //글 수정 페이지 이동
    @GetMapping("/challenges/{postId}")
    public String updateBoard(@PathVariable String postId){
        return "updateboard";
    }
    //유저 수정 페이지 이동
    @GetMapping("/users/{userId}")
    public String updateUser(@PathVariable String userId){
        return "mypage";
    }
    //로그아웃시 홈으로 이동
    @GetMapping("/users/logout")
    public String logoutUser(){
        return "index";
    }
}

