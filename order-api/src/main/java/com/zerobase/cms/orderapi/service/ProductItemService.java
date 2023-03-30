package com.zerobase.cms.orderapi.service;

import com.zerobase.cms.orderapi.domain.model.Product;
import com.zerobase.cms.orderapi.domain.model.ProductItem;
import com.zerobase.cms.orderapi.domain.product.AddProductItemForm;
import com.zerobase.cms.orderapi.domain.repository.ProductItemRepository;
import com.zerobase.cms.orderapi.domain.repository.ProductRepository;
import com.zerobase.cms.orderapi.exception.CustomException;
import com.zerobase.cms.orderapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductItemService {
	private final ProductItemRepository productItemRepository;
	private final ProductRepository productRepository;

	@Transactional
	public Product addProductItem(Long sellerId, AddProductItemForm form) {
		Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		if (product.getProductItems().stream()
			.anyMatch(item -> item.getName().equals(form.getName()))) {
			throw new CustomException(ErrorCode.SAME_ITEM_NAME);
		}

		ProductItem productItem = ProductItem.of(sellerId, form);
		product.getProductItems().add(productItem);
		return product;
	}
}
