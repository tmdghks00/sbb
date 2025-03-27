package com.mysite.sbb; // 패키지 선언

import lombok.Getter; // Lombok의 @Getter 어노테이션 사용을 위한 import
import lombok.RequiredArgsConstructor; // Lombok의 @RequiredArgsConstructor 어노테이션 사용을 위한 import

// final 필드(hello, lombok)를 매개변수로 받는 생성자를 자동으로 생성해주는 Lombok 어노테이션
@RequiredArgsConstructor
// 모든 필드의 Getter 메서드를 자동으로 생성해주는 Lombok 어노테이션
@Getter
public class HelloLombok {

    // final 키워드가 붙은 필드는 생성자에서 초기화해야 함
    private final String hello;
    private final int lombok;

    public static void main(String[] args) {
        // Lombok이 자동 생성한 생성자를 사용하여 객체 생성
        HelloLombok helloLombok = new HelloLombok("헬로", 5);

        // Lombok이 자동 생성한 Getter 메서드를 통해 필드 값 출력
        System.out.println(helloLombok.getHello()); // "헬로" 출력
        System.out.println(helloLombok.getLombok()); // 5 출력
    }
}
