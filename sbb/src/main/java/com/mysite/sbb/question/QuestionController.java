package com.mysite.sbb.question;

import java.security.Principal;
import java.util.List;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/question") // "/question" 경로에 대한 요청을 처리하는 컨트롤러
@RequiredArgsConstructor // final이 붙은 속성을 자동으로 주입하는 생성자 생성
@Controller // 스프링 MVC 컨트롤러로 등록
public class QuestionController {

    private final QuestionService questionService; // 질문 관련 서비스
    private final UserService userService; // 사용자 관련 서비스

    // 질문 목록 조회 (페이징 처리 & 검색 포함)
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = this.questionService.getList(page, kw); // 페이지와 검색어로 질문 조회
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw); // 검색어 유지
        return "question_list"; // 질문 목록 페이지 반환
    }

    // 질문 상세 페이지 조회
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id); // 해당 질문 조회
        model.addAttribute("question", question);
        return "question_detail"; // 질문 상세 페이지 반환
    }

    // 질문 작성 폼 페이지 (로그인한 사용자만 접근 가능)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form"; // 질문 작성 폼 페이지 반환
    }

    // 질문 작성 (로그인한 사용자만 가능)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) { // 입력값 검증 실패 시 다시 폼 페이지로 이동
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName()); // 현재 로그인한 사용자 조회
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser); // 질문 저장
        return "redirect:/question/list"; // 질문 목록으로 이동
    }

    // 질문 수정 폼 페이지 (로그인한 사용자만 접근 가능)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) { // 작성자 본인인지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject()); // 기존 제목 설정
        questionForm.setContent(question.getContent()); // 기존 내용 설정
        return "question_form"; // 수정 폼 페이지 반환
    }

    // 질문 수정 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) { // 입력값 검증 실패 시 다시 폼 페이지로 이동
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) { // 작성자 본인인지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent()); // 질문 수정
        return String.format("redirect:/question/detail/%s", id); // 수정 후 상세 페이지로 이동
    }

    // 질문 삭제 (로그인한 사용자만 가능)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) { // 작성자 본인인지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question); // 질문 삭제
        return "redirect:/"; // 질문 삭제 후 홈으로 이동
    }

    // 질문 추천 (로그인한 사용자만 가능)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName()); // 현재 로그인한 사용자 정보 가져오기
        this.questionService.vote(question, siteUser); // 사용자를 추천인으로 추가
        return String.format("redirect:/question/detail/%s", id); // 추천 후 질문 상세 페이지로 이동
    }
}
