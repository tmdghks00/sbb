package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="제목은 필수항목입니다.") // 제목이 비어있으면 오류 메시지 출력
    @Size(max=200) // 최대 길이 200자로 제한
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.") // 내용이 비어있으면 오류 메시지 출력
    private String content;
}
