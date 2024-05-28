package com.abs.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FixedDepositDTOTest {

	@Test
	void testBuilder() {
		new FixedDepositDTO().builder().toString();
	}

}
