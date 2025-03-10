package com.mysite.sbb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드를 포함한 생성자 자동 생성
@Getter // Getter 메서드 자동 생성
public class HelloLombok {

    private final String hello;
    private final int lombok;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok("헬로", 5);
        System.out.println(helloLombok.getHello()); // "헬로" 출력
        System.out.println(helloLombok.getLombok()); // 5 출력
    }
}
