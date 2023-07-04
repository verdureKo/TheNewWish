package com.sparta.wish.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 전략
    private Long id;
    private String username;
    private String password;
    private String introduction; //프로필 소개글
    private String image; // 사진 URL


    //JPA 사용시 기본 생성자 필요
    public User() {
    }

    public User(String username, String password, String introduction, String image) {
        this.username = username;
        this.password = password;
        this.introduction = introduction;
        this.image = image;
    }
}
