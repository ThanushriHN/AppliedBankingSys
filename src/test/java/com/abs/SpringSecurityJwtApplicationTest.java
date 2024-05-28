package com.abs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.web.client.RestTemplate;
import com.abs.dto.FixedDepositDTO;
import com.abs.dto.ReturnAmount;
import com.abs.model.FixedDepositAccount;
import com.abs.repository.FixedAccountRepository;

import com.abs.service.FixedDepositAccountService;
import com.abs.utility.RestTemplateConfig;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Sql(scripts = { "classpath:TestDB/CreateStatements.sql", "classpath:TestDB/InsertStatements.sql" })
class SpringSecurityJwtApplicationTest {
	
	Logger logger = LoggerFactory.getLogger(SpringSecurityJwtApplicationTest.class);


	@LocalServerPort
	private int port;

	@Autowired
	FixedAccountRepository fixedAccountRepository;

	@Autowired
	FixedDepositAccountService depositAccountService;


	@BeforeAll
	public static void init() {
		RestTemplateConfig config = new RestTemplateConfig();
		restTemplate = config.restTemplateWithJwt(JwtToken);
	}

	private static final double PRINCIPAL = 10000.0;
	private static final int TERM_IN_MONTHS = 24;
	private static final String ACCOUNT_NUMBER = "1234";
	private static final String BRANCH = "abc";
	private static RestTemplate restTemplate;
	private String baseURL = "http://localhost:";
	private static String CUSTOMER_EMAIL = "sahana@gmail.com";

	private static final int ACCOUNT_ID = 1;

	private static String JwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZ5YUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6InJlYWQsdXBkYXRlLGNyZWF0ZSxST0xFX01BTkFHRVIsZGVsZXRlIiwiaWF0IjoxNzE2ODc0NjU2LCJleHAiOjE3MTcyMjAyNTZ9.7v30UW78Vq7LWDBIVL-lMAzREeAtBDWpbJtn-knWfzc";

	 @Test
	void testOpenFixedDepositAccount_Success() {
		baseURL = baseURL.concat(port + "").concat("/customer/createFixedDepositAccount/" + CUSTOMER_EMAIL);
		FixedDepositDTO dto = FixedDepositDTO.builder().accountNumber(ACCOUNT_NUMBER).branch(BRANCH)
				.principal(PRINCIPAL).termInMonths(TERM_IN_MONTHS).build();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + "" + JwtToken);
		HttpEntity<FixedDepositDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<FixedDepositDTO> entity = restTemplate.postForEntity(baseURL, requestEntity,
				FixedDepositDTO.class);
		assertNotNull(entity.getBody());
		assertEquals(dto.getAccountNumber(), entity.getBody().getAccountNumber());

	 }

	 @Test
	void closeFixedDepositAccount() {
		baseURL = baseURL.concat(port + "").concat("/customer/closeAccount/" + ACCOUNT_ID);

		Optional<FixedDepositAccount> account = fixedAccountRepository.findById(ACCOUNT_ID);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + "" + JwtToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		 ResponseEntity<ReturnAmount> returnAmount = restTemplate.exchange(baseURL, HttpMethod.DELETE, entity, ReturnAmount.class);
		assertEquals(account.get().getMaturityAmount(), returnAmount.getBody().getMaturityAmount());
		assertNotNull(account.get());

	}

	 @Test
	 void testGetAccountDetails_Success() {
		baseURL = baseURL.concat(port + "").concat("/customer/getAccountDetails/" + ACCOUNT_ID);

		FixedDepositDTO dto = FixedDepositDTO.builder().accountNumber(ACCOUNT_NUMBER).branch(BRANCH)
				.principal(PRINCIPAL).termInMonths(TERM_IN_MONTHS).build();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + "" + JwtToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<FixedDepositDTO> depositDTO = restTemplate.getForEntity(baseURL, FixedDepositDTO.class, entity);
		assertEquals(dto.getPrincipal(), depositDTO.getBody().getPrincipal());
		assertEquals(dto.getBranch(), depositDTO.getBody().getBranch());

	}


}
