package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 증가 (MySQL의 AUTO_INCREMENT)
    private Integer id;

    @Column(columnDefinition = "TEXT") // 긴 문자열 저장을 위한 TEXT 타입 컬럼
    private String content;

    private LocalDateTime createDate; // 답변 작성 시간

    @ManyToOne // 다대일(N:1) 관계 => 여러 개의 답변이 하나의 질문에 속함
    private Question question;

    @ManyToOne // 다대일(N:1) 관계 => 여러 개의 답변이 하나의 사용자(작성자)에 속함
    private SiteUser author; // 작성자

    private LocalDateTime modifyDate; // 답변 수정 시간

    @ManyToMany // 다대다(N:N) 관계 => 여러 사용자가 하나의 답변을 추천할 수 있음
    Set<SiteUser> voter;
}


