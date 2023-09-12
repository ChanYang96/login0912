package com.example.login0910.service;

import com.example.login0910.filter.CustomUserDetailService;
import com.example.login0910.filter.JwtTokenProvider;
import com.example.login0910.filter.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LogInService{
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService userDetailService; // 토큰 발행 파트에서 가져온다

    @Autowired
    public LogInService(JwtTokenProvider jwtTokenProvider, CustomUserDetailService userDetailService) {
        this.jwtTokenProvider = jwtTokenProvider;//해당 클래스에서 사용
        this.userDetailService = userDetailService;
    }
    public Token login(String email, String password) {
        // 사용자명(이메일)과 비밀번호로 사용
        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        System.out.println("여기서 에러가 발생한다." + userDetails.getUsername());

        if (userDetails != null) {
            System.out.println("첫번째");
            // userDetails가 null이 아닌 경우에만 비밀번호를 확인
            if (userDetails.getPassword().equals(password)) {
                // 존재한다면
                System.out.println("두번째");
                Token token = jwtTokenProvider.createAccessToken(email, userDetails.getAuthorities());
                return token;
            } else {
                System.out.println("세번째");
                // 인증 실패 시 예외 처리 또는 다른 로직을 수행할 수 있습니다.
                throw new UsernameNotFoundException("로그인 실패, 다시 확인해 주세요.");
            }
        } else {
            System.out.println("네번째");
            // userDetails가 null인 경우 사용자를 찾을 수 없다고 처리할 수 있습니다.
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}
