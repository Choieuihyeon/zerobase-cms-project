package com.zerobase.cms.orderapi.service;

import com.zerobase.cms.orderapi.domain.model.Product;
import com.zerobase.cms.orderapi.domain.repository.ProductRepository;
import com.zerobase.cms.orderapi.exception.CustomException;
import com.zerobase.cms.orderapi.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
	private final ProductRepository productRepository;

	public List<Product> searchByName(String name) {
		return productRepository.searchByName(name);
	}


	public Product getByProductId(Long productId) {
		return productRepository.findWithProductItemsById(productId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
	}

	public List<Product> getListByProductIds(List<Long> productIds) {
		return productRepository.findAllByIdIn(productIds);
	}

}
