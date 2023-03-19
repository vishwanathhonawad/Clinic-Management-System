package com.demo.spring.dto;


public class PatientDiagnosticDTO {


	private int patientDiagnosticId;
	
	private int diagnosticId;
	
	private int patientId;
	
	public PatientDiagnosticDTO() {
	}
	

	public PatientDiagnosticDTO(int diagnosticId, int patientId) {
		super();
		this.diagnosticId = diagnosticId;
		this.patientId = patientId;
	}


	public PatientDiagnosticDTO(int patientDiagnosticId, int diagnosticId, int patientId) {
		super();
		this.patientDiagnosticId = patientDiagnosticId;
		this.diagnosticId = diagnosticId;
		this.patientId = patientId;
	}

	public int getPatientDiagnosticId() {
		return patientDiagnosticId;
	}

	public void setPatientDiagnosticId(int patientDiagnosticId) {
		this.patientDiagnosticId = patientDiagnosticId;
	}

	public int getDiagnosticId() {
		return diagnosticId;
	}

	public void setDiagnosticId(int diagnosticId) {
		this.diagnosticId = diagnosticId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
}
