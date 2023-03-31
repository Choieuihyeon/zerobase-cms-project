package com.zerobase.cms.orderapi.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.cms.orderapi.domain.model.Product;
import com.zerobase.cms.orderapi.domain.model.QProduct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Product> searchByName(String name) {
		String search = "%" + name + "%";	// 앞 뒤에 구분자 명시

		QProduct product = QProduct.product;
		return queryFactory.selectFrom(product)
			.where(product.name.like(search))
			.fetch();
	}
}
