package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DOCTOR_SPECIALITY")
public class DoctorSpeciality {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "doctorSpeciality_id")
	@SequenceGenerator(sequenceName = "doctorSpeciality_sequence",allocationSize = 1,initialValue = 1,name="doctorSpeciality_id")
	@Column(name="ID")
	private int doctorSpecialityId;

	@Column(name="DOCTOR_ID")
	private int doctorId;
	
	@Column(name="SPECIALITY_ID")
	private int specialityId;
	
	public DoctorSpeciality() {
	}

	public DoctorSpeciality(int doctorId, int specialityId) {
		super();
		this.doctorId = doctorId;
		this.specialityId = specialityId;
	}

	public DoctorSpeciality(int doctorSpecialityId, int doctorId, int specialityId) {
		super();
		this.doctorSpecialityId = doctorSpecialityId;
		this.doctorId = doctorId;
		this.specialityId = specialityId;
	}



	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(int specialityId) {
		this.specialityId = specialityId;
	}

	public int getDoctorSpecialityId() {
		return doctorSpecialityId;
	}

	public void setDoctorSpecialityId(int doctorSpecialityId) {
		this.doctorSpecialityId = doctorSpecialityId;
	}
	
}
