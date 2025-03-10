package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 스프링 MVC 컨트롤러 등록
public class HelloController {

    @GetMapping("/hello") // "/hello" 요청을 처리
    @ResponseBody // 응답 본문으로 문자열 반환
    public String hello() {
        return "Hello Spring Boot Board"; // 문자열 반환
    }
}
