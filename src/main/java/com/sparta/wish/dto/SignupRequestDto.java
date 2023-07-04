package com.sparta.wish.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.ModelAttribute;

@Setter
@Getter
@ToString
public class SignupRequestDto {
    private String username;
    private String password;
    private String introduction; //프로필 소개글
    private String image; // 프로필 사진

}
