package com.zerobase.userapi.domain.model;

import com.zerobase.userapi.domain.SignUpForm;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride (forClass = BaseEntity.class)
public class Customer extends BaseEntity {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;
	private String name;
	private String password;
	private String phone;
	private LocalDate birth;

	private LocalDateTime verifyExpiredAt;
	private String verificationCode;
	private boolean verify;

	@Column(columnDefinition = "int default 0")
	private Integer balance;

	public static Customer from(SignUpForm form) {
		return Customer.builder()
			.email(form.getEmail().toLowerCase(Locale.ROOT))
			.name(form.getName())
			.password(form.getPassword())
			.birth(form.getBirth())
			.phone(form.getPhone())
			.verify(false)
			.build();
	}

}
