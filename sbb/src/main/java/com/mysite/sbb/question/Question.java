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
@Entity // JPA 엔티티 선언
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 증가 (MySQL AUTO_INCREMENT)
    private Integer id;

    @Column(length = 200) // 최대 200자 제한
    private String subject; // 질문 제목

    @Column(columnDefinition = "TEXT") // 긴 글을 저장할 수 있도록 TEXT 타입 사용
    private String content; // 질문 내용

    private LocalDateTime createDate; // 질문 작성 시간

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    // 질문(One)과 답변(Many) 간의 관계 설정, 질문 삭제 시 답변도 같이 삭제됨
    private List<Answer> answerList;

    @ManyToOne // 다대일(N:1) 관계, 여러 개의 질문이 하나의 사용자에게 속함
    private SiteUser author; // 질문 작성자

    private LocalDateTime modifyDate; // 질문 수정 시간

    @ManyToMany // 다대다(N:N) 관계, 여러 사용자가 질문을 추천할 수 있음
    Set<SiteUser> voter; // 추천한 사용자 목록
}
