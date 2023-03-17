package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto_Increment
    private long id;

    private LocalDateTime createDate; // 데이터 생성날짜

    private LocalDateTime modifydate; // 데이터 수정 날짜
    private String title;

    private String body;
}
