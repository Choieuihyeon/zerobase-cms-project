package com.zerobase.userapi;


import com.zerobase.userapi.domain.SignUpForm;
import com.zerobase.userapi.domain.model.Customer;
import com.zerobase.userapi.domain.model.Seller;
import com.zerobase.userapi.user.client.service.customer.SignUpCustomerService;
import com.zerobase.userapi.user.client.service.seller.SellerService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

@WebAppConfiguration
@SpringBootTest
class SignUpSellerServiceTest {

	@Autowired
	private SellerService service;

	@Test
	void signUp() {
		SignUpForm form = SignUpForm.builder()
			.name("name")
			.birth(LocalDate.now())
			.email("abc@gmail.com")
			.password("1")
			.phone("0100000000")
			.build();

		Seller s = service.signUp(form);
		Assert.notNull(s.getId());
		Assert.notNull(s.getCreatedAt());

	}
}