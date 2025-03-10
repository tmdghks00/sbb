package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/answer") // "/answer" 경로에 대한 요청을 처리하는 컨트롤러
@RequiredArgsConstructor // 생성자 주입을 자동으로 생성해주는 Lombok 어노테이션
@Controller // 스프링 MVC 컨트롤러로 등록
public class AnswerController {

    private final QuestionService questionService; // 질문 관련 서비스
    private final AnswerService answerService; // 답변 관련 서비스
    private final UserService userService; // 사용자 관련 서비스

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 답변 작성 가능
    @PostMapping("/create/{id}") // POST 방식으로 "/answer/create/{id}" 요청을 처리
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = this.questionService.getQuestion(id); // 해당 ID의 질문 가져오기
        SiteUser siteUser = this.userService.getUser(principal.getName()); // 현재 로그인한 사용자 정보 가져오기

        if (bindingResult.hasErrors()) { // 폼 유효성 검사 실패 시
            model.addAttribute("question", question);
            return "question_detail"; // 질문 상세 페이지로 이동
        }

        Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser); // 답변 생성
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId()); // 답변 작성 후 해당 질문 상세 페이지로 리다이렉트
    }

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능
    @GetMapping("/modify/{id}") // GET 방식으로 "/answer/modify/{id}" 요청을 처리 (수정 페이지 이동)
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id); // ID로 답변 가져오기
        if (!answer.getAuthor().getUsername().equals(principal.getName())) { // 본인이 작성한 답변인지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent()); // 기존 답변 내용을 폼에 설정
        return "answer_form"; // 수정 폼 페이지 반환
    }

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능
    @PostMapping("/modify/{id}") // POST 방식으로 "/answer/modify/{id}" 요청을 처리 (수정 요청)
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) { // 폼 유효성 검사 실패 시
            return "answer_form"; // 수정 폼 페이지로 다시 이동
        }

        Answer answer = this.answerService.getAnswer(id); // ID로 답변 가져오기
        if (!answer.getAuthor().getUsername().equals(principal.getName())) { // 본인이 작성한 답변인지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        this.answerService.modify(answer, answerForm.getContent()); // 답변 수정
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId()); // 수정 후 해당 질문 상세 페이지로 리다이렉트
    }

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 삭제 가능
    @GetMapping("/delete/{id}") // GET 방식으로 "/answer/delete/{id}" 요청을 처리
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id); // ID로 답변 가져오기
        if (!answer.getAuthor().getUsername().equals(principal.getName())) { // 본인이 작성한 답변인지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer); // 답변 삭제
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId()); // 삭제 후 질문 상세 페이지로 이동
    }

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 추천 가능
    @GetMapping("/vote/{id}") // GET 방식으로 "/answer/vote/{id}" 요청을 처리
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id); // ID로 답변 가져오기
        SiteUser siteUser = this.userService.getUser(principal.getName()); // 현재 로그인한 사용자 정보 가져오기
        this.answerService.vote(answer, siteUser); // 답변 추천 처리
        return String.format("redirect:/question/detail/%s#answer_%s", // 추천 후 해당 답변으로 이동 (앵커 추가)
                answer.getQuestion().getId(), answer.getId());
    }
}
