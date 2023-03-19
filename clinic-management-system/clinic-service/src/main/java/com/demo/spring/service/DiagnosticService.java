package com.demo.spring.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Diagnostic;
import com.demo.spring.exception.DiagnosticTestExistsException;
import com.demo.spring.exception.DiagnosticTestNotExistsException;
import com.demo.spring.exception.NoDiagnosticTestExistsException;
import com.demo.spring.repository.DiagnosticRepository;
import com.demo.spring.repository.PatientDiagnosticRepository;
import com.demo.spring.util.Message;

@Service
public class DiagnosticService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	DiagnosticRepository diagnosticRepository;
	
	@Autowired
	PatientDiagnosticRepository patientDiagnosticRepository;

	public List<Diagnostic> listAllDiagnosticTest() throws NoDiagnosticTestExistsException {
		List<Diagnostic> listDiagnostic = diagnosticRepository.findAll();
		if (listDiagnostic.isEmpty()) {
			logger.error("Exception : No Diagnostic Test Found Exception thrown ");
			throw new NoDiagnosticTestExistsException();
		} else {
			logger.info("Returned list of all diagnostic test ");
			return listDiagnostic;
		}
	}

	public Message saveDiagnosticTest(Diagnostic diagnostic) throws DiagnosticTestExistsException {
		List<Diagnostic> listTest = diagnosticRepository.findByDiagnosticName(diagnostic.getTestName());
		if (listTest.isEmpty()) {
			diagnosticRepository.save(diagnostic);
			logger.info("Diagnostic method saved Successfully");
			return new Message("Test Saved Successfully");
		} else {
			logger.error("Exception : Diagnostic Test Exists Exception thrown");
			throw new DiagnosticTestExistsException();
		}
	}

	public Message deleteDiagnosticTest(Integer testId) throws DiagnosticTestNotExistsException {
		if (diagnosticRepository.existsById(testId)) {
			patientDiagnosticRepository.deleteAllByTestId(testId);
			diagnosticRepository.deleteById(testId);
			logger.info("Diagnostic test deleted successfully ");
			return new Message("Test deleted Successfully");
		} else {
			logger.error("Exception : Diagnostic Test Not Exists Exception Thrown");
			throw new DiagnosticTestNotExistsException();
		}
	}

}
