package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    @Size(min = 3, max = 25) // 사용자 ID 길이 제한 (3~25자)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1; // 비밀번호 입력

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2; // 비밀번호 확인

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email // 이메일 형식 검증
    private String email;
}
