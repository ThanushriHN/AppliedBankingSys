package com.abs.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AddressDTOTest {

	@Test
	void testNoArgsConstructor() {
		AddressDTO addressDTO = new AddressDTO();
		assertNotNull(addressDTO);
	}

	@Test
	void testAllArgsConstructor() {
		AddressDTO addressDTO = new AddressDTO("10th cross", "xyz", "xyz");
		assertNotNull(addressDTO);
		assertEquals("10th cross", addressDTO.getAddressLine());
		assertEquals("xyz", addressDTO.getCustomerState());
		assertEquals("xyz", addressDTO.getCustomerCountry());
	}

	@Test
	void testBuilder() {
		AddressDTO addressDTO = AddressDTO.builder().addressLine("10th cross").customerState("xyz")
				.customerCountry("xyz").build();

		assertNotNull(addressDTO);
		assertEquals("10th cross", addressDTO.getAddressLine());
		assertEquals("xyz", addressDTO.getCustomerState());
		assertEquals("xyz", addressDTO.getCustomerCountry());
	}

	@Test
	void testSettersAndGetters() {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAddressLine("10th cross");
		addressDTO.setCustomerState("xyz");
		addressDTO.setCustomerCountry("xyz");

		assertEquals("10th cross", addressDTO.getAddressLine());
		assertEquals("xyz", addressDTO.getCustomerState());
		assertEquals("xyz", addressDTO.getCustomerCountry());
	}

	@Test
	void testToString() {
		AddressDTO addressDTO = new AddressDTO("10th cross", "xyz", "xyz");
		String expectedString = "AddressDTO(addressLine=10th cross, customerState=xyz, customerCountry=xyz)";
		assertEquals(expectedString, addressDTO.toString());
	}

	@Test
	void test_Builder() {
		new AddressDTO().builder().toString();
	}
}
