package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Diagnostic;

public interface DiagnosticRepository extends JpaRepository<Diagnostic, Integer> {
	
	@Query("select d from Diagnostic d where d.testName=:testName")
	public List<Diagnostic> findByDiagnosticName(String testName);
	
}
