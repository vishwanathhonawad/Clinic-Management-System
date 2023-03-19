package com.demo.spring.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PatientDiagnosticTest {

	@Test
	void testPatientDiagnostic() {
		PatientDiagnostic  patientDiagnostic=new PatientDiagnostic();
		patientDiagnostic.setDiagnosticId(1);
		patientDiagnostic.setPatientId(1);
		patientDiagnostic.setPatientDiagnosticId(1);
		
		assertEquals(1, patientDiagnostic.getPatientId());
		assertEquals(1, patientDiagnostic.getDiagnosticId());
		assertEquals(1, patientDiagnostic.getPatientDiagnosticId());
		
	}

}
