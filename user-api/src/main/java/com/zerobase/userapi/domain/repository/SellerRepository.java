package com.zerobase.userapi.domain.repository;

import com.zerobase.userapi.domain.model.Seller;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

	Optional<Seller> findByIdAndEmail(Long id, String email);

	Optional<Seller> findByEmailAndPasswordAndVerifyIsTrue(String email, String password);

	Optional<Seller> findByEmail(String email);
}
