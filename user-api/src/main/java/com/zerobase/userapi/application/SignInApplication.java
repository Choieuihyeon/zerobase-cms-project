package com.zerobase.userapi.application;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserType;
import com.zerobase.userapi.domain.SignInForm;
import com.zerobase.userapi.domain.model.Customer;
import com.zerobase.userapi.exception.CustomException;
import com.zerobase.userapi.exception.ErrorCode;
import com.zerobase.userapi.user.client.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

	private final CustomerService customerService;
	private final JwtAuthenticationProvider provider;

	public String customerLoginToken(SignInForm form) {
		// 1. 로그인 가능 여부
		Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
			.orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
		// 2. 토큰을 발행하고

		// 3. 토큰을 response 한다.
		return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
	}


}
