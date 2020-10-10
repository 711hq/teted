package com.etc.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;
    @Column(name = "article_title")
    private String articleTitle;//文章标题
    @Column(name = "article_content")
    private String articleContent;//文章内容
    @Column(name = "article_date")
    private String articleDate;
    @Column(name = "author_id")
    private int authorId;
}
