package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"), // 관리자 권한
    USER("ROLE_USER");   // 일반 사용자 권한

    // 생성자를 통해 enum 상수에 지정된 값(value)을 설정
    UserRole(String value) {
        this.value = value;
    }

    private String value; // 권한 값을 저장하는 변수
}
