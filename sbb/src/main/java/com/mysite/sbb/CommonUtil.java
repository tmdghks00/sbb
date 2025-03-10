package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component // 스프링 빈으로 등록
public class CommonUtil {

    // Markdown을 HTML로 변환하는 메서드
    public String markdown(String markdown) {
        Parser parser = Parser.builder().build(); // Markdown 파서를 생성
        Node document = parser.parse(markdown); // 입력받은 Markdown을 파싱하여 문서 객체 생성
        HtmlRenderer renderer = HtmlRenderer.builder().build(); // HTML 렌더러 생성
        return renderer.render(document); // 변환된 HTML 반환
    }
}
