package com.zerobase.userapi.user.client.service;

import com.zerobase.userapi.domain.model.Customer;
import com.zerobase.userapi.domain.repository.CustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
	private final CustomerRepository customerRepository;

	public Optional<Customer> findValidCustomer(String email, String password) {
		return customerRepository.findByEmail(email).stream()
			.filter(customer -> customer.getPassword().equals(password) && customer.isVerify())
			.findFirst();
	}
}
