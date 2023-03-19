package com.demo.spring.dto;

public class DoctorSpeciality {

	private int doctorSpecialityId;

	private int doctorId;

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
