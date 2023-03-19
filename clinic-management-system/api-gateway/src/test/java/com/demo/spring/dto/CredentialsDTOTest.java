package com.demo.spring.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CredentialsDTOTest {

	@Test
	void testCredentials() {
		CredentialsDTO credentialsDTO=new CredentialsDTO();
		credentialsDTO.setPassword("admin");
		credentialsDTO.setUserName("admin");
		
		assertEquals("admin", credentialsDTO.getUserName());
		assertEquals("admin", credentialsDTO.getPassword());
	}

}
