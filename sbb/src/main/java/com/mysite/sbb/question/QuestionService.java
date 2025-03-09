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

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else { //  id값에 해당하는 질문 데이터가 없을 경우
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user) {// 제목과 내용, 사용자명을 입력받아 질문으로 저장
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page) { //  정수 타입의 페이지 번호를 입력받아 해당 페이지의 Page 객체를 리턴
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    // 질문 제목과 내용을 수정할 수 있는 메서드
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) { // 질문 데이터를 삭제
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) { // 추천 기능
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
