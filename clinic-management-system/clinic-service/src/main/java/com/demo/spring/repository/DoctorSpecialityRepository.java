package com.demo.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.DoctorSpeciality;

public interface DoctorSpecialityRepository extends JpaRepository<DoctorSpeciality, Integer> {

	@Query("select d from DoctorSpeciality d where d.doctorId=:doctorId and d.specialityId=:specialityId")
	public List<DoctorSpeciality> getAllByDocIdAndSpecId(int doctorId, int specialityId);

	@Query("delete DoctorSpeciality d where d.doctorId=:doctorId and d.specialityId=:specialityId")
	@Modifying
	@Transactional
	public int deleteDoctorSpeciality(int doctorId, int specialityId);
}
