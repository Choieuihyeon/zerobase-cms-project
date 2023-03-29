package com.zerobase.userapi.user.client.service.customer;

import com.zerobase.userapi.domain.customer.ChangeBalanceForm;
import com.zerobase.userapi.domain.model.CustomerBalanceHistory;
import com.zerobase.userapi.domain.repository.CustomerBalanceHistoryRepository;
import com.zerobase.userapi.domain.repository.CustomerRepository;
import com.zerobase.userapi.exception.CustomException;
import com.zerobase.userapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {

	private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;
	private final CustomerRepository customerRepository;

	@Transactional(noRollbackFor = {CustomException.class}) // 해당 익셉션이 나와있을때 noRollbackFor 함
	public CustomerBalanceHistory changeBalance(Long customerId, ChangeBalanceForm form) throws CustomException{
		CustomerBalanceHistory customerBalanceHistory =
			customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(customerId)
				.orElse(CustomerBalanceHistory.builder()
					.changeMoney(0)
					.currentMoney(0)
					.customer(customerRepository.findById(customerId)
						.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)))
					.build());

		if(customerBalanceHistory.getCurrentMoney() + form.getMoney() < 0) {
			throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
		}

		customerBalanceHistory = CustomerBalanceHistory.builder()
			.changeMoney(form.getMoney())
			.currentMoney(customerBalanceHistory.getCurrentMoney() + form.getMoney())
			.description(form.getMessage())
			.fromMessage(form.getFrom())
			.customer(customerBalanceHistory.getCustomer())
			.build();
		customerBalanceHistory.getCustomer().setBalance(customerBalanceHistory.getCurrentMoney());

		return customerBalanceHistoryRepository.save(customerBalanceHistory);
	}
}