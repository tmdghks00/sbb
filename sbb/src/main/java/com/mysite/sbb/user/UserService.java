package com.mysite.sbb.user;

import com.mysite.sbb.DataNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
@Service // 스프링 서비스 빈으로 등록
public class UserService {

    private final UserRepository userRepository; // 사용자 저장소
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    // 사용자 생성
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // 비밀번호 암호화 후 저장
        this.userRepository.save(user);
        return user;
    }

    // 사용자 조회
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
