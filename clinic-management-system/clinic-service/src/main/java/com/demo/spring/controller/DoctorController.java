package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Doctor;
import com.demo.spring.exception.DoctorNotFoundException;
import com.demo.spring.service.DoctorService;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("/clinic/doctor")
@OpenAPI30
public class DoctorController {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	DoctorService doctorService;

	/**
	 * this method will return list of all doctors
	 * 
	 * @return
	 * @throws DoctorNotFoundException
	 */
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.list.doctor")
	public ResponseEntity<List<Doctor>> listAllDoctors() throws DoctorNotFoundException {
		logger.info("This list all doctotrs method had call to doctor service ");
		return ResponseEntity.ok(doctorService.listAllDoctors());
	}

	/**
	 * this method will return doctor object if exists by id
	 * 
	 * @param doctorId
	 * @return
	 * @throws DoctorNotFoundException
	 */
	@GetMapping(path = "/find/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.find.doctor")
	public ResponseEntity<Doctor> getDoctor(@PathVariable("doctorId") Integer doctorId) throws DoctorNotFoundException {
		logger.info("This find doctotrs method had call to find doctor service ");
		return ResponseEntity.ok(doctorService.findDoctorById(doctorId));
	}

}
