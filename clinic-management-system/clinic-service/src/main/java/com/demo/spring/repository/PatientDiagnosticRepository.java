package com.demo.spring.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.PatientDiagnostic;

public interface PatientDiagnosticRepository extends JpaRepository<PatientDiagnostic, Integer> {

	@Query("delete PatientDiagnostic p where p.diagnosticId=:diagnosticId")
	@Transactional
	@Modifying
	public int deleteAllByTestId(int diagnosticId);
}
