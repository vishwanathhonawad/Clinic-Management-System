package com.demo.spring.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MessageTest {

	@Test
	void testMessage() {
		Message message=new Message();
		message.setStatus("vishwa");
		assertEquals("vishwa", message.getStatus());

	}


}
