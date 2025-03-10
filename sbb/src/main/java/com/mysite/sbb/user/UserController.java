package com.mysite.sbb.user;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 자동 주입
@Controller // 스프링 MVC 컨트롤러로 등록
@RequestMapping("/user") // "/user" 경로에 대한 요청을 처리
public class UserController {

    private final UserService userService; // 사용자 관련 서비스

    // 회원가입 폼 페이지
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form"; // 회원가입 폼 페이지 반환
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // 입력값 검증 실패 시 다시 폼 페이지로 이동
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) { // 중복된 사용자 처리
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) { // 기타 예외 처리
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/"; // 회원가입 성공 후 홈으로 이동
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login_form"; // 로그인 폼 페이지 반환
    }
}
