package com.demo.spring.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.dto.PatientDTO;
import com.demo.spring.entity.Diagnostic;
import com.demo.spring.entity.PatientDiagnostic;
import com.demo.spring.exception.DiagnosticTestNotExistsException;
import com.demo.spring.exception.PatientNotExistsException;
import com.demo.spring.repository.DiagnosticRepository;
import com.demo.spring.repository.PatientDiagnosticRepository;
import com.demo.spring.util.Message;
import com.demo.spring.util.ServerConfiguration;

@Service
@EnableConfigurationProperties(ServerConfiguration.class)
public class PatientDiagnosticService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	ServerConfiguration server;

	@Autowired
	PatientDiagnosticRepository patientDiagnosticRepository;
	
	@Autowired
	DiagnosticRepository diagnosticRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	public Message addTestToPatient(int patientId,int testId) throws DiagnosticTestNotExistsException, PatientNotExistsException {
		PatientDTO patientDTO = restTemplate
				.getForEntity(server.getPatientServer()+"/patient/{patientId}", PatientDTO.class, patientId).getBody();
		if (patientDTO != null && patientDTO.getPatientId() == patientId) {
			Optional<Diagnostic> diagnosticOp=diagnosticRepository.findById(testId);
			if(diagnosticOp.isPresent()) {
				PatientDiagnostic patientDiagnostic=new PatientDiagnostic(patientId,testId);
				patientDiagnosticRepository.save(patientDiagnostic);
				logger.info("Test added successfully for patientID {}",patientId);
				return new Message("Test added successfully");
			}else {
				logger.error("Exception : Diagnostic Test Not Exists Exception Thrown ");
				throw new DiagnosticTestNotExistsException();
			}		} else {
			logger.error("Exception : Patient Not Exists Exception Thrown ");
			throw new PatientNotExistsException();
		}
	}
}
