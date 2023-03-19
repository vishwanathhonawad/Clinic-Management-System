package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PATIENT_DIAGNOSTIC")
public class PatientDiagnostic {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "patientDiagnostic_Id")
	@SequenceGenerator(sequenceName = "patientDiagnostic_sequence",allocationSize = 1,initialValue = 1,name="patientDiagnostic_Id")
	@Column(name="ID")
	private int patientDiagnosticId;
	
	@Column(name="PATIENT_ID")
	private int patientId;
	
	@Column(name="DIAGNOSTIC_ID")
	private int diagnosticId;
	
	public PatientDiagnostic() {
	}
	

	public PatientDiagnostic(int patientId,int diagnosticId ) {
		super();
		this.patientId = patientId;
		this.diagnosticId = diagnosticId;
		
	}


	public PatientDiagnostic(int patientDiagnosticId, int patientId,int diagnosticId) {
		super();
		this.patientDiagnosticId = patientDiagnosticId;
		this.patientId = patientId;
		this.diagnosticId = diagnosticId;
		
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
