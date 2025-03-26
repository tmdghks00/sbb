package com.mysite.sbb.user;

import com.mysite.sbb.DataNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor // final이나 @NonNull이 붙은 필드에 대해 생성자를 자동으로 생성
@Service // 이 클래스를 스프링의 서비스 컴포넌트로 등록
public class UserService {

    private final UserRepository userRepository; // 사용자 정보를 다루는 JPA 인터페이스
    private final PasswordEncoder passwordEncoder; // 비밀번호를 암호화하기 위한 인코더

    // 사용자 생성 메서드
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser(); // SiteUser 객체 생성
        user.setUsername(username); // 사용자 이름 설정
        user.setEmail(email); // 이메일 설정
        user.setPassword(passwordEncoder.encode(password)); // 비밀번호를 암호화하여 설정
        this.userRepository.save(user); // 사용자 정보를 DB에 저장
        return user; // 저장된 사용자 반환
    }

    // 사용자 이름(username)으로 사용자 조회
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username); // username으로 사용자 검색
        if (siteUser.isPresent()) {
            return siteUser.get(); // 존재하면 사용자 객체 반환
        } else {
            throw new DataNotFoundException("siteuser not found"); // 없으면 예외 발생
        }
    }
}
