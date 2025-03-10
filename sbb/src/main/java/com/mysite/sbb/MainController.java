package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 스프링 MVC 컨트롤러 등록
public class MainController {

    @GetMapping("/sbb") // "/sbb" 요청 처리
    @ResponseBody // 응답 본문으로 문자열 반환
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다."; // 메시지 출력
    }

    @GetMapping("/") // 루트 URL 요청 처리
    public String root() {
        return "redirect:/question/list"; // 질문 목록 페이지로 리다이렉트
    }
}
