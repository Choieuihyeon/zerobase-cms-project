package com.zerobase.cms.orderapi.application;

import com.zerobase.cms.orderapi.client.UserClient;
import com.zerobase.cms.orderapi.client.user.ChangeBalanceForm;
import com.zerobase.cms.orderapi.client.user.CustomerDto;
import com.zerobase.cms.orderapi.domain.model.ProductItem;
import com.zerobase.cms.orderapi.domain.redis.Cart;
import com.zerobase.cms.orderapi.exception.CustomException;
import com.zerobase.cms.orderapi.exception.ErrorCode;
import com.zerobase.cms.orderapi.service.ProductItemService;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderApplication {

	private final CartApplication cartApplication;
	private final UserClient userClient;
	private final ProductItemService productItemService;

	@Transactional
	public void order(String token, Cart cart) {
		// 1번 : 주문 시 기존 카트 버림.
		// 2번 : 선택주문 : 내가 사지 않은 아이템을 살려야 함.
		// -- 숙제
		// 현재에서는 1번 케이스로 진행!
		Cart orderCart = cartApplication.refreshCart(cart);
//		Cart orderCart = cartApplication.getCart(customerId);
		if (orderCart.getMessages().size() > 0) {
			// 문제가 있음.
			throw new CustomException(ErrorCode.ORDER_FAIL_CHECK_CART);
		}
		CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();

		int totalPrice = getTotalPrice(cart);
		if (customerDto.getBalance() < getTotalPrice(cart)) {
			throw new CustomException(ErrorCode.ORDER_FAIL_NO_MONEY);
		}

		// 롤백 계획에 대해서 생각해야 함.
		userClient.changeBalance(token, ChangeBalanceForm.builder()
			.from("USER")
			.message("Order")
			.money(-totalPrice)
				.build());

		for (Cart.Product product : orderCart.getProducts()) {
			for (Cart.ProductItem cartItem : product.getItems()) {
				ProductItem productItem = productItemService.getProductItem(cartItem.getId());
				productItem.setCount(productItem.getCount() - cartItem.getCount());
			}
		}
	}

	public Integer getTotalPrice(Cart cart) {

		return cart.getProducts().stream().flatMapToInt(product ->
			product.getItems().stream().flatMapToInt(productItem -> IntStream.of(
				productItem.getPrice() * productItem.getCount()))).sum();
	}

	// 결제를 위해 필요한 것
	// 1 : 물건들이 전부 주문 가능한 상태인지 확인
	// 2 : 가격 변동이 있었는지에 대해 확인
	// 3 : 고객의 돈이 충분한지
	// 4 : 결제 & 상품의 재고 관리

}
