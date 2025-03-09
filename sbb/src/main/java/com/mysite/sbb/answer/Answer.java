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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne // N:1 관계 표현 =>  답변=Many  질문 = One
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate; // 수정일시

    @ManyToMany
    Set<SiteUser> voter;

}
