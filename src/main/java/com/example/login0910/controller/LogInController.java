package com.example.login0910.controller;

import com.example.login0910.domain.form.LoginForm;
import com.example.login0910.filter.Token;
import com.example.login0910.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LogInController {
    private final LogInService logInService;

    @Autowired
    public LogInController(LogInService logInService) {
        this.logInService = logInService;
    }

    @PostMapping("/loginuser")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        // 이메일 형식이 유효한지 검사
        if (!isValidEmailFormat(loginForm.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 형식이 올바르지 않습니다.");
        }

        try {
            // 로그인 서비스를 호출하여 로그인 시도
            Token token = logInService.login(loginForm.getEmail(), loginForm.getPw());
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            // 사용자가 존재하지 않는 경우
            System.out.println("존재 안함");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 계정 입니다.");
        } catch (BadCredentialsException e) {
            // 비밀번호가 잘못된 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호를 잘못 입력 하셨습니다.");
        }
    }

    // 이메일 형식이 맞는지
    private boolean isValidEmailFormat(String email) {
        // @가 없으면 이메일 서식이 아니라고 가정
        return email.contains("@");
    }
}
