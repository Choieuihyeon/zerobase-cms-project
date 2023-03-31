package com.zerobase.cms.orderapi.domain.repository;

import com.zerobase.cms.orderapi.domain.model.Product;
import java.util.List;
import org.springframework.stereotype.Repository;

public interface ProductRepositoryCustom {
	List<Product> searchByName(String name);
}
