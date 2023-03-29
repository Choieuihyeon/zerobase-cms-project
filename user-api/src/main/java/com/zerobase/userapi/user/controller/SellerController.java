package com.zerobase.userapi.user.controller;

import static com.zerobase.userapi.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserVo;
import com.zerobase.userapi.domain.seller.SellerDto;
import com.zerobase.userapi.domain.model.Seller;
import com.zerobase.userapi.exception.CustomException;
import com.zerobase.userapi.user.client.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

	private final JwtAuthenticationProvider provider;
	private final SellerService sellerService;

	@GetMapping ("/getInfo")
	public ResponseEntity<SellerDto> getInfo(@RequestHeader (name = "X-AUTH-TOKEN") String token) {
		UserVo vo = provider.getUserVo(token);
		Seller s = sellerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(
			() -> new CustomException(NOT_FOUND_USER));

		return ResponseEntity.ok(SellerDto.from(s));
	}
}
