package com.sparta.wish.dto.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.ModelAttribute;

@Setter
@Getter
@ToString
public class SignupRequestDto { // 회원가입

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String introduction; //프로필 소개

    private String image; // 프로필 사진

}
