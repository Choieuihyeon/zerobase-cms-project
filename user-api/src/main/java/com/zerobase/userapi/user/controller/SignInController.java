package com.zerobase.userapi.user.controller;

import com.zerobase.userapi.application.SignInApplication;
import com.zerobase.userapi.domain.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/singIn")
@RequiredArgsConstructor
public class SignInController {

	private final SignInApplication signInApplication;

	@PostMapping("/customer")
	public ResponseEntity<String> signInCustomer(@RequestBody SignInForm form) {
		return ResponseEntity.ok(signInApplication.customerLoginToken(form));
	}

}
