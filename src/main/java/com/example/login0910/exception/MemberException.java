package com.example.login0910.exception;
import lombok.Getter;
@Getter
public class MemberException extends RuntimeException {
    private final ErrorCode errorCode;
    public MemberException(ErrorCode errorCode) {
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }
}
