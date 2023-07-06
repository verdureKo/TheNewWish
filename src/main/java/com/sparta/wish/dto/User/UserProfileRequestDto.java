package com.sparta.wish.dto.User;

import com.sparta.wish.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRequestDto {
    // 수정 된 정보를 보낸다.

    private String username;

    private String checkpassword;
    private String updatepassword;

    private String introduction; //프로필 소개

    private String image; // 프로필 사진


}
