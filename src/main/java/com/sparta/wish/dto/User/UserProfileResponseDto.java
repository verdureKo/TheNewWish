package com.sparta.wish.dto.User;

import com.sparta.wish.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDto {
    // 수정 된 정보를 보낸다.

    private String username;

    private String password;

    private String introduction; //프로필 소개

    private String image; // 프로필 사진

    public UserProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.introduction = user.getIntroduction();
        this.image = user.getImage();
    }
}
