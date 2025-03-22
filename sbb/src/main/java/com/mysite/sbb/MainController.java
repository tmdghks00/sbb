package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 스프링 MVC 컨트롤러 등록
public class MainController {

/*    만약 @ResponseBody 애너테이션을 생략한다면 스프링 부트는
    'index'라는 문자열을 리턴하는 대신 index라는 이름의 템플릿 파일을 찾게됨 */
    @GetMapping("/sbb") // "/sbb" 요청 처리
    @ResponseBody //  URL 요청에 대한 응답으로 문자열 반환
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다."; // 메시지 출력
    }

    @GetMapping("/") // 루트 URL 요청 처리
    public String root() {
        return "redirect:/question/list"; // 질문 목록 페이지로 리다이렉트
    }
}
