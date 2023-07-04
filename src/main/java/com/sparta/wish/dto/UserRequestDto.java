package com.sparta.wish.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRequestDto {
    String username;
    String password;
    String introduction;
    String image;
}
