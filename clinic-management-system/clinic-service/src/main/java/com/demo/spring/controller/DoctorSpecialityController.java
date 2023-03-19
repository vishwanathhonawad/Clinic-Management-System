package com.demo.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.exception.DoctorNotFoundException;
import com.demo.spring.exception.DoctorSpecialityExists;
import com.demo.spring.exception.DoctorSpecialityNotExists;
import com.demo.spring.exception.SpecialityNotExistsException;
import com.demo.spring.service.DoctorSpecialityService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("/clinic/doctorspeciality")
@OpenAPI30
public class DoctorSpecialityController {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	DoctorSpecialityService doctorSpecialityService;

	/**
	 * this method will add a specialty to a doctor
	 * 
	 * @param doctorId
	 * @param specialityId
	 * @return
	 * @throws DoctorSpecialityExists
	 * @throws DoctorNotFoundException
	 * @throws SpecialityNotExistsException
	 */
	@PostMapping(path = "/{doctorId}/{specialityId}")
	@Timed(value = "request.add.speciality.doctor")
	public ResponseEntity<Message> addSpecialityToDoctor(@PathVariable("doctorId") Integer doctorId,
			@PathVariable("specialityId") Integer specialityId)
			throws DoctorSpecialityExists, DoctorNotFoundException, SpecialityNotExistsException {
		logger.info("This add specialty to doctotrs method had call to doctor speciality service ");
		return ResponseEntity.ok(doctorSpecialityService.addDoctorToSpeciality(doctorId, specialityId));
	}

	/**
	 * this method will remove specialty from a doctors
	 * 
	 * @param doctorId
	 * @param specialityId
	 * @return
	 * @throws DoctorSpecialityNotExists
	 * @throws SpecialityNotExistsException
	 * @throws DoctorNotFoundException
	 */
	@DeleteMapping(path = "/{doctorId}/{specialityId}")
	@Timed(value = "request.remove.speciality.doctor")
	public ResponseEntity<Message> removeSpecialityFromDoctor(@PathVariable("doctorId") Integer doctorId,
			@PathVariable("specialityId") Integer specialityId)
			throws DoctorSpecialityNotExists, SpecialityNotExistsException, DoctorNotFoundException {
		logger.info("This remove specialty to doctotrs method had call to doctor speciality service ");
		return ResponseEntity.ok(doctorSpecialityService.removeDoctorFromSpeciality(doctorId, specialityId));
	}

}
