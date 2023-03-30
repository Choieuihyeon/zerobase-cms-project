package com.zerobase.cms.orderapi.domain.model;

import com.zerobase.cms.orderapi.domain.product.AddProductForm;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited	// name이나 description이 바뀌면 무조건 저장
@AuditOverride(forClass = BaseEntity.class)
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long sellerId;

	private String name;

	private String description;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private List<ProductItem> productItems = new ArrayList<>();

	public static Product of(Long sellerId, AddProductForm form) {
		return Product.builder()
			.sellerId(sellerId)
			.name(form.getName())
			.description(form.getDescription())
			.productItems(form.getItems().stream()
				.map(piFrom -> ProductItem.of(sellerId, piFrom)).collect(Collectors.toList()))
			.build();
	}
}
