package com.zerobase.cms.orderapi.domain.repository;

import com.zerobase.cms.orderapi.domain.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

}
