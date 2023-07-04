package com.sparta.wish.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardRequestDto {
    String title;
    String content;
    int timer;


}
