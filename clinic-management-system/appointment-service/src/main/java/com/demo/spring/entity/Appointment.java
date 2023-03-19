package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="APPOINTMENT")
public class Appointment {
	
	@Id
	@SequenceGenerator(sequenceName = "appointment_Id", initialValue = 1, allocationSize = 1, name = "appointment_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_Id")
	@Column(name="APPOINTMENT_ID")
	private int appointmentId;
	
	@Column(name="DOCTOR_ID")
	private int doctorId;
	
	@Column(name="PATIENT_ID")
	private int patientId;
	
	@Column(name="DATE")
	private String date;
	
	public Appointment() {
	}

	public Appointment(int appointmentId, int doctorId, int patientId, String date) {
		super();
		this.appointmentId = appointmentId;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.date = date;
	}
	public Appointment(int doctorId, int patientId, String date) {
		super();
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.date = date;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	

}
