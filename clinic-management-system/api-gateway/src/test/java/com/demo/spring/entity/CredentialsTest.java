package com.demo.spring.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CredentialsTest {

	@Test
	void testCredentials() {
		Credentials credentials=new Credentials();
		credentials.setPassword("admin");
		credentials.setUserName("admin");
		
		assertEquals("admin", credentials.getUserName());
		assertEquals("admin", credentials.getPassword());
	}

}
