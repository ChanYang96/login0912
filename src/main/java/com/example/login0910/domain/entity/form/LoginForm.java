package com.example.login0910.domain.entity.form;

import com.example.login0910.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginForm { //폼을 통해서 로그인에 필요한 이메일과 패스워드 데이터를 받는다.
    private String email;
    private String pw;
    public static Member from (LoginForm lfrom){//폼으로 받은 값을 엔티티로 전달
        return Member.builder()
                .email(lfrom.getEmail())
                .pw(lfrom.getPw())
                .build();
    }
}
