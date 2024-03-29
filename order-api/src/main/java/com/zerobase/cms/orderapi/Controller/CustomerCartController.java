package com.zerobase.cms.orderapi.Controller;

import com.zerobase.cms.orderapi.application.CartApplication;
import com.zerobase.cms.orderapi.application.OrderApplication;
import com.zerobase.cms.orderapi.domain.product.AddProductCartForm;
import com.zerobase.cms.orderapi.domain.redis.Cart;
import com.zerobase.cms.orderapi.service.CartService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/cart")
@RequiredArgsConstructor
public class CustomerCartController {

	// 임시 코드
	private final CartApplication cartApplication;
	private final OrderApplication orderApplication;
	private final JwtAuthenticationProvider provider;

	@PostMapping
	public ResponseEntity<Cart> addCart(
		@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody AddProductCartForm form) {
		return ResponseEntity.ok(cartApplication.addCart(provider.getUserVo(token).getId(), form));
	}

	@GetMapping
	public ResponseEntity<Cart> showCart(
		@RequestHeader(name = "X-AUTH-TOKEN") String token) {
		return ResponseEntity.ok(cartApplication.getCart(provider.getUserVo(token).getId()));
	}

	@PutMapping
	public ResponseEntity<Cart> updateCart(
		@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody Cart cart) {
		return ResponseEntity.ok(cartApplication.updateCart(provider.getUserVo(token).getId(), cart));
	}

	@PostMapping("/order")
	public ResponseEntity<Cart> order(
		@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody Cart cart) {
		orderApplication.order(token, cart);
		return ResponseEntity.ok().build();
	}

}
