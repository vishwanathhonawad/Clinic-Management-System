package com.demo.spring.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiagnosticTest {

	@Test
	void testDiagnostic() {
		Diagnostic diagnostic=new Diagnostic();
		diagnostic.setTestcId(1);
		diagnostic.setTestName("ECG");
		
		assertEquals(1,diagnostic.getTestcId());
		assertEquals("ECG",diagnostic.getTestName());
	}

}
