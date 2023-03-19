package com.demo.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	
	public List<Appointment> findAllByPatientId(Integer patientId);
	
	@Query("select a from Appointment a where a.doctorId=:doctorId and a.date=:date")
	@Modifying
	@Transactional
	public List<Appointment> findAllByDoctorIdAndDate(Integer doctorId,String date);
	
	@Query("select a from Appointment a where a.patientId=:patientId and a.doctorId=:doctorId and a.date=:date")
	@Modifying
	@Transactional
	public List<Appointment> findExistsAppointment(Integer patientId,Integer doctorId,String date);
	
}
