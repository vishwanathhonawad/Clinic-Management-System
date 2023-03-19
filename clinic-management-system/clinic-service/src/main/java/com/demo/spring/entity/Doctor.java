package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DOCTOR")
public class Doctor {
	
	@Id
	@SequenceGenerator(sequenceName = "doctor_Id", initialValue = 1, allocationSize = 1, name = "doctor_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_Id")
	@Column(name="DOCTOR_ID")
	private int doctorId;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String  lastName;
	
	@Column(name="EMAIL")
	private String email;
	
	public Doctor() {
	}


	public Doctor(int doctorId, String firstName, String lastName, String email) {
		super();
		this.doctorId = doctorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public Doctor(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
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
