package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dto.PatientDTO;
import com.demo.spring.entity.Patient;
import com.demo.spring.exception.EmailExistsException;
import com.demo.spring.exception.NoPatientExistsByNameException;
import com.demo.spring.exception.PatientExistsException;
import com.demo.spring.exception.PatientNotExistsException;
import com.demo.spring.service.PatientService;
import com.demo.spring.util.Message;

import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("/patient")
@OpenAPI30

public class PatientController {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	@Autowired
	PatientService patientService;

	/**
	 * This method will register a patient
	 * 
	 * @param patientDTO
	 *                   <p>
	 *                   hello
	 *                   </p>
	 * @return
	 * @throws PatientExistsException
	 * @throws EmailExistsException
	 */
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> registerPatient(@RequestBody PatientDTO patientDTO) throws PatientExistsException {
		logger.info("This /patient/ endpoint had a call to patient service");
		return ResponseEntity.ok(patientService.registerPatient(patientDTO));
	}

	/**
	 * This method will return patient object by using id if exists
	 * 
	 * @param patientId
	 * @return
	 * @throws PatientNotExistsException
	 */
	@GetMapping(path = "/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> findPatientById(@PathVariable("patientId") Integer patientId)
			throws PatientNotExistsException {
		return ResponseEntity.ok(patientService.findPatientById(patientId));
	}

	/**
	 * This method will return object list with same first names
	 * 
	 * @param firstName
	 * @return
	 * @throws NoPatientExistsByNameException
	 */
	@GetMapping(path = "/name/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Patient>> findPatientByName(@PathVariable("firstName") String firstName)
			throws NoPatientExistsByNameException {
		return ResponseEntity.ok(patientService.findAllPatientByName(firstName));
	}

	/**
	 * This method will update patient data
	 * 
	 * @param patientDTO
	 * @return
	 * @throws PatientNotExistsException
	 * @throws EmailExistsException
	 */
	@PatchMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> updatePatient(@RequestBody PatientDTO patientDTO)
			throws PatientNotExistsException, EmailExistsException {
		return ResponseEntity.ok(patientService.updatePatient(patientDTO));
	}

}
