package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Doctor;
import com.demo.spring.exception.DoctorNotFoundException;
import com.demo.spring.exception.SpecialityNotExistsException;
import com.demo.spring.service.SpecialityService;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("/clinic/speciality")
@OpenAPI30
public class SpecialityController {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	SpecialityService specialityService;

	/**
	 * this method will return list of doctor with given specialty
	 * 
	 * @param specialityId
	 * @return
	 * @throws DoctorNotFoundException
	 * @throws SpecialityNotExistsException
	 */
	@GetMapping(path = "/{specialityId}")
	@Timed(value = "request.list.doctor.speciality")
	public ResponseEntity<List<Doctor>> listAllDoctorsOfSpeciality(@PathVariable("specialityId") int specialityId)
			throws DoctorNotFoundException, SpecialityNotExistsException {
		logger.info("This list all doctor of specialty method had call to doctor speciality service ");
		return ResponseEntity.ok(specialityService.listAllDoctorsOfSpeciality(specialityId));
	}

}
