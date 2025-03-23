package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.mysite.sbb.answer.Answer;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor // 생성자 자동 주입
@Service // 서비스 레이어 선언
public class QuestionService {

    private final QuestionRepository questionRepository; // 질문 데이터 저장소

    // 검색 기능: 검색어(kw)가 제목, 내용, 작성자 또는 답변에 포함된 질문 찾기
    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); // 중복 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT); // 질문 작성자와 조인
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT); // 답변과 조인
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT); // 답변 작성자와 조인

                return cb.or(
                        cb.like(q.get("subject"), "%" + kw + "%"),  // 제목 검색
                        cb.like(q.get("content"), "%" + kw + "%"),  // 내용 검색
                        cb.like(u1.get("username"), "%" + kw + "%"),  // 질문 작성자 검색
                        cb.like(a.get("content"), "%" + kw + "%"),  // 답변 내용 검색
                        cb.like(u2.get("username"), "%" + kw + "%") // 답변 작성자 검색
                );
            }
        };
    }

    // 전체 질문 목록 조회
    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    // 특정 ID의 질문을 조회하는 메서드 (없으면 예외 발생)
    public Question getQuestion(Integer id) {
        // 주어진 ID에 해당하는 질문을 데이터베이스에서 찾음 (Optional로 반환)
        Optional<Question> question = this.questionRepository.findById(id);

        // 질문이 존재하면 해당 객체를 반환
        if (question.isPresent()) {
            return question.get();
        } else {
            // 질문이 존재하지 않으면 예외를 발생시킴
            throw new DataNotFoundException("question not found");
        }
    }

    // 새로운 질문 생성
    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject); // 제목 설정
        q.setContent(content); // 내용 설정
        q.setCreateDate(LocalDateTime.now()); // 작성 날짜 설정
        q.setAuthor(user); // 작성자 설정
        this.questionRepository.save(q); // 데이터베이스에 저장
    }

    // 페이징 처리된 질문 목록 조회 (검색 포함)
    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // 최신 질문이 위로 오도록 정렬
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 페이지 크기 10
        Specification<Question> spec = search(kw); // 검색 조건 설정
        return this.questionRepository.findAll(spec, pageable); // 검색어와 함께 페이징된 질문 리스트 반환
    }

    // 질문 수정 (제목, 내용 변경)
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now()); // 수정 날짜 업데이트
        this.questionRepository.save(question);
    }

    // 질문 삭제
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    // 질문 추천 (추천한 사용자 추가)
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
