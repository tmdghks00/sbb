package com.mysite.sbb;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.QuestionService;

@SpringBootTest //  SbbApplicationTests 클래스가 스프링 부트의 테스트 클래스임을 의미
class SbbApplicationTests {

    @Autowired // 의존성 주입(DI) 기능을 사용하여 객체를 주입
    private QuestionService questionService;

    @Test // testJpa 메서드가 테스트 메서드임을 나타냄
    @Transactional //  메서드가 종료될 때까지 DB 세션이 유지
        // 트랜잭션 추가
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.questionService.create(subject, content);
        }
    }
}
