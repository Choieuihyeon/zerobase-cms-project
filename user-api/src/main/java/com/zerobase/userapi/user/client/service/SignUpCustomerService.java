package com.zerobase.userapi.user.client.service;

import com.zerobase.userapi.domain.SignUpForm;
import com.zerobase.userapi.domain.model.Customer;
import com.zerobase.userapi.domain.repository.CustomerRepository;
import com.zerobase.userapi.exception.CustomException;
import com.zerobase.userapi.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

	private final CustomerRepository customerRepository;

	/**
	 * 회원가입
	 */
	public Customer signUp(SignUpForm form) {
		return customerRepository.save(Customer.from(form));
	}

	/**
	 * 이메일 중복 여부 확인
	 */
	public boolean isEmailExist(String email) {
		return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
			.isPresent();

	}

	@Transactional
	public void verifyEmail(String email, String code) {
		Customer customer = customerRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		if (customer.isVerify()) {
			throw new CustomException(ErrorCode.ALREADY_VERIFY);
		} else if (!customer.getVerificationCode().equals(code)) {
			throw new CustomException(ErrorCode.WRONG_VERIFY);
		} else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
			throw new CustomException(ErrorCode.EXPIRE_CODE);
		}
		customer.setVerify(true);

	}

	/**
	 * customer 계정 변경
	 */
	// save를 하지 않아도 자연스럽게 바뀐게 있으면 저장 -> Transactional
	@Transactional
	public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
		Optional<Customer> customerOptional = customerRepository.findById(customerId);

		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			customer.setVerificationCode(verificationCode);
			customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
			return customer.getVerifyExpiredAt();
		}
		throw new CustomException(ErrorCode.NOT_FOUND_USER);
	}

}
