package com.mysite.sbb.answer;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor // 생성자 자동 주입
@Service // 스프링 서비스 객체로 등록
public class AnswerService {

    private final AnswerRepository answerRepository; // 답변 데이터 처리하는 JPA 저장소

    // 답변 생성 메서드
    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content); // 답변 내용 설정
        answer.setCreateDate(LocalDateTime.now()); // 작성 날짜 설정
        answer.setQuestion(question); // 해당 질문과 연결
        answer.setAuthor(author); // 답변 작성자 설정
        this.answerRepository.save(answer); // DB에 저장
        return answer;
    }

    // 답변 조회 메서드
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found"); // 답변이 없으면 예외 발생
        }
    }

    // 답변 수정 메서드
    public void modify(Answer answer, String content) {
        answer.setContent(content); // 새로운 내용으로 변경
        answer.setModifyDate(LocalDateTime.now()); // 수정 날짜 설정
        this.answerRepository.save(answer); // 변경된 내용 저장
    }

    // 답변 삭제 메서드
    public void delete(Answer answer) {
        this.answerRepository.delete(answer); // 해당 답변 삭제
    }

    // 답변 추천(좋아요) 메서드
    public void vote(Answer answer, SiteUser siteUser) {
        if (!answer.getVoter().contains(siteUser)) { // 이미 추천했는지 확인
            answer.getVoter().add(siteUser);
            this.answerRepository.save(answer); // 변경 내용 저장
        }
    }

    public Page<Answer> getAnswersByQuestion(Question question, Pageable pageable) {
        return this.answerRepository.findByQuestion(question, pageable);
    }
}

