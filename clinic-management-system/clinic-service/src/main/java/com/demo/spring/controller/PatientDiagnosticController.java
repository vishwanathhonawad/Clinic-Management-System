package com.demo.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.exception.DiagnosticTestNotExistsException;
import com.demo.spring.exception.PatientNotExistsException;
import com.demo.spring.service.PatientDiagnosticService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("/clinic/patientdiagnostic")
@OpenAPI30
public class PatientDiagnosticController {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	PatientDiagnosticService patientDiagnosticService;

	/**
	 * this method will add test to a patient
	 * 
	 * @param patientId
	 * @param testId
	 * @return
	 * @throws PatientNotExistsException
	 * @throws DiagnosticTestNotExistsException
	 */
	@PostMapping(path = "/save/{patientId}/{testId}")
	@Timed(value = "request.add.test.patient")
	public ResponseEntity<Message> addTestToPatient(@PathVariable("patientId") int patientId,
			@PathVariable("testId") int testId) throws PatientNotExistsException, DiagnosticTestNotExistsException {
		logger.info("This add test to patient method had call to patient test service ");
		return ResponseEntity.ok(patientDiagnosticService.addTestToPatient(patientId, testId));

	}

}
