package com.abs.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abs.dto.FixedDepositDTO;
import com.abs.dto.ReturnAmount;
import com.abs.model.Customer;
import com.abs.model.FixedDepositAccount;
import com.abs.model.User;
import com.abs.repository.CustomerRepository;
import com.abs.repository.FixedAccountRepository;
import com.abs.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FixedDepositAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FixedDepositAccountService.class);

	@Autowired
	private FixedAccountRepository depositRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<FixedDepositDTO> createFixedDepositAccount(FixedDepositDTO depositDTO, String customerEmail,
			HttpServletRequest request) {

		Customer customer = customerRepository.findByCustomerEmail(customerEmail);
		if (!isValidDeposit(depositDTO.getPrincipal())) {
			throw new IllegalArgumentException("Minimum deposit amount not met.");
		}
		if (!isValidTerm(depositDTO.getTermInMonths())) {
			throw new IllegalArgumentException("Invalid term.");
		}

		FixedDepositAccount account = toEntity(depositDTO);
		double interestRate = calculateInterestRate(account.getTermInMonths());
		double maturityAmount = calculateMaturityAmount(account.getPrincipal(), account.getTermInMonths(),
				interestRate);

		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String email;

		jwt = authHeader.substring(7);
		email = jwtService.extractUsername(jwt);
		Optional<User> user = userRepository.findByEmail(email);

		account.setAddedBy(user.get().getFirstName() + " " + user.get().getLastName());
		account.setUpdatedBy(user.get().getFirstName() + " " + user.get().getLastName());

		account.setCustomer(customer);
		account.setAddedOn(LocalDateTime.now());
		account.setUpdatedOn(LocalDateTime.now());
		account.setInterestRate(interestRate);
		account.setMaturityAmount(maturityAmount);

		FixedDepositAccount savedAccount = depositRepository.save(account);
		FixedDepositDTO fixedDepositDTO = toDto(savedAccount);
		return ResponseEntity.ok(fixedDepositDTO);
	}

	private boolean isValidDeposit(double principal) {
		return principal >= 1000;
	}

	private boolean isValidTerm(int termInMonths) {
		return termInMonths >= 1 && termInMonths <= 120;
	}

	private double calculateInterestRate(int termInMonths) {
		if (termInMonths <= 90) {
			return 0.045;
		} else {
			return 0.07;
		}
	}

	private double calculateMaturityAmount(double principal, int termInMonths, double interestRate) {
		return principal * Math.pow(1 + interestRate, termInMonths / 12.0);
	}

	public FixedDepositAccount toEntity(FixedDepositDTO dto) {
		FixedDepositAccount entity = new FixedDepositAccount();
		entity.setPrincipal(dto.getPrincipal());
		entity.setTermInMonths(dto.getTermInMonths());
		entity.setAccountNumber(dto.getAccountNumber());
		entity.setBranch(dto.getBranch());
		return entity;
	}

	public FixedDepositDTO toDto(FixedDepositAccount entity) {
		FixedDepositDTO dto = new FixedDepositDTO();
		dto.setPrincipal(entity.getPrincipal());
		dto.setTermInMonths(entity.getTermInMonths());
		dto.setAccountNumber(entity.getAccountNumber());
		dto.setBranch(entity.getBranch());
		return dto;
	}

	public ReturnAmount closeAccount(int accountId) {
		FixedDepositAccount account = depositRepository.findById(accountId).orElseThrow();
			int closeTime = LocalDateTime.now().getMonthValue() - account.getAddedOn().getMonthValue();

			if (account.getTermInMonths() >= closeTime) {
				Customer customer=account.getCustomer();
				customer.getAccounts().remove(account);
				customerRepository.save(customer);
				depositRepository.delete(account);
				LOGGER.info("Account closed Success");
				ReturnAmount returnAmount = maptoReturnMoney(account);
				return returnAmount;
			} else {	
				LOGGER.info("Account can't be closed");
				ReturnAmount returnAmount = maptoReturnMoney(account);
				returnAmount.setMaturityAmount(0.0);
				return returnAmount;
			}
	}

	public ReturnAmount maptoReturnMoney(FixedDepositAccount account) {
		ReturnAmount returnAmount = new ReturnAmount();
		returnAmount.setMaturityAmount(account.getMaturityAmount());
		return returnAmount;
	}

	public ResponseEntity<FixedDepositDTO> getAccountDetails(int accountId) {
        try {
            FixedDepositAccount account = depositRepository.findById(accountId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
            FixedDepositDTO depositDTO = toDto(account);
            LOGGER.info("Account details retrieved successfully for accountId: {}", accountId);
            return ResponseEntity.ok(depositDTO);
        } catch (ResponseStatusException e) {
            LOGGER.error("Account not found for accountId: {}", accountId, e);
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } 
    }


}
