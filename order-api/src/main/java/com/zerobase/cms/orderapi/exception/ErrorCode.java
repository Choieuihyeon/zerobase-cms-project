package com.zerobase.cms.orderapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
	ALREADY_REGISTER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다.");

	private final HttpStatus httpStatus;
	private final String detail;
}
