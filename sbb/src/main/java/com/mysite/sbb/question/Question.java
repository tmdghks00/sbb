package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // 질문을 삭제하면 그에 달린 답변들도 모두 삭제
    private List<Answer> answerList;

    @ManyToOne // 사용자 한 명이 질문을 여러 개 작성
    private SiteUser author;

    private LocalDateTime modifyDate; // 수정 일시

    @ManyToMany // 하나의 질문에 여러 사람이 추천할 수 있고 한 사람이 여러 개의 질문을 추천 가능함
    Set<SiteUser> voter; // 추천인을 저장하기 위한 속성

}
