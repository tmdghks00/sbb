package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Page<Answer> findByQuestion(Question question, Pageable pageable); // 특정 질문의 답변을 페이징 처리하여 조회
}