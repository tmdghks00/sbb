package com.mysite.sbb.question;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 스프링 빈으로 등록
public interface QuestionRepository extends JpaRepository<Question, Integer> {
// JpaRepository 는 CRUD 작업을 처리하는 메서드들을 이미 내장
    Question findBySubject(String subject); // 제목으로 질문 찾기

    Question findBySubjectAndContent(String subject, String content); // 제목과 내용이 모두 일치하는 질문 찾기

    List<Question> findBySubjectLike(String subject); // 제목에 특정 문자열이 포함된 질문 찾기

    Page<Question> findAll(Pageable pageable); // 페이징 처리를 위한 메서드

    Page<Question> findAll(Specification<Question> spec, Pageable pageable); // 검색어를 포함한 페이징 조회
}
