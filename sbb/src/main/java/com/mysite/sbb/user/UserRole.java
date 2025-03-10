package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"), // 관리자 권한
    USER("ROLE_USER"); // 일반 사용자 권한

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
