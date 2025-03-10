package com.mysite.sbb.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // JPA 엔티티 선언
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 증가 (MySQL AUTO_INCREMENT)
    private Long id;

    @Column(unique = true) // 사용자ID는 중복 저장 불가능 (유니크 제약조건)
    private String username;

    private String password; // 암호화된 비밀번호 저장

    @Column(unique = true) // 이메일도 중복 저장 불가능 (유니크 제약조건)
    private String email;
}
