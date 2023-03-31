package com.zerobase.cms.orderapi.service;

import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.cms.orderapi.domain.model.Product;
import com.zerobase.cms.orderapi.domain.product.AddProductForm;
import com.zerobase.cms.orderapi.domain.product.AddProductItemForm;
import com.zerobase.cms.orderapi.domain.repository.ProductItemRepository;
import com.zerobase.cms.orderapi.domain.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

	@Test
	void ADD_PRODUCT_TEST() {
		Long sellerId = 1L;

		AddProductForm form = makeProductForm("나이키 에어포스", "신발", 3);

		Product p = productService.addProduct(sellerId, form);

		Product result = productRepository.findWithProductItemsById(p.getId()).get();

		assertNotNull(result);

		// 나머지 필드들에 대한 검증 ( 숙제 )
		assertEquals(result.getName(), "나이키 에어포스");
		assertEquals(result.getDescription(), "신발");

		assertEquals(result.getProductItems().size(), 3);
		assertEquals(result.getProductItems().get(0).getName(), "나이키 에어포스");
		assertEquals(result.getProductItems().get(0).getPrice(), 10000);
		assertEquals(result.getProductItems().get(0).getCount(), 1);

	}

	private static AddProductForm makeProductForm(String name, String description, int itemCount) {
		List<AddProductItemForm> itemForms = new ArrayList<>();
		for (int i = 0; i < itemCount; i++) {
			itemForms.add(makeProductItemForm(null, name + i));
		}
		return AddProductForm.builder()
			.name(name)
			.description(description)
			.items(itemForms)
			.build();
	}

	private static AddProductItemForm makeProductItemForm(Long productId, String name) {
		return AddProductItemForm.builder()
			.productId(productId)
			.name(name)
			.price(10000)
			.count(1)
			.build();
	}
}