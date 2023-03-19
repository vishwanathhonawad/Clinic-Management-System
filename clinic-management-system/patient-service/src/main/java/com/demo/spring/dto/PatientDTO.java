package com.demo.spring.dto;

public class PatientDTO {

	private int patientId;
	private String firstName;
	private String lastName;
	private String email;

	public PatientDTO() {
	}

	public PatientDTO(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public PatientDTO(int patientId, String firstName, String lastName, String email) {
		this(firstName, lastName, email);
		this.patientId = patientId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
