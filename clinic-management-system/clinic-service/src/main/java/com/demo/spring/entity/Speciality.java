package com.demo.spring.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SPECIALITY")
public class Speciality {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "speciality_Id")
	@SequenceGenerator(sequenceName = "speciality_sequence",allocationSize = 1,initialValue = 1,name="speciality_Id")
	@Column(name = "SPECIALITY_ID")
	private int specialityId;

	@Column(name = "SPECIALITY_NAME")
	private String specialityName;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DOCTOR_SPECIALITY", joinColumns = @JoinColumn(name = "SPECIALITY_ID"),
        inverseJoinColumns = @JoinColumn(name = "DOCTOR_ID"))
    private Set<Doctor> doctors=new HashSet<>();

	public Speciality() {
	}

	public Speciality(int specialityId, String specialityName) {
		super();
		this.specialityId = specialityId;
		this.specialityName = specialityName;
	}
	public Speciality( String specialityName) {
		super();
		this.specialityName = specialityName;
	}

	public int getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(int specialityId) {
		this.specialityId = specialityId;
	}

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}
	

}
