package com.demo.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

	public List<Patient> findAllByFirstName(String firstName);

	public List<Patient> findAllByEmail(String email);

	@Query("update Patient p set p.firstName=:firstName , p.lastName=:lastName ,p.email=:email where p.patientId=:patientId")
	@Transactional
	@Modifying
	public int updatePatient(int patientId, String firstName, String lastName, String email);

	@Query("select p from Patient p where p.email=:email and p.patientId!=:patientId")
	public List<Patient> findAllByEmailNotId(int patientId,String email );
}
