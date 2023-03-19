package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dto.AppointmentDTO;
import com.demo.spring.entity.Appointment;
import com.demo.spring.exception.DoctorAppointmentNotFound;
import com.demo.spring.exception.DoctorNotFoundException;
import com.demo.spring.exception.PatientAppointmentNotFound;
import com.demo.spring.exception.PatientNotExistsException;
import com.demo.spring.service.AppointmentService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("/appointment")
@OpenAPI30
public class AppointmentController {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	AppointmentService appointmentService;

	/**
	 * this method will add an appointment for a patient
	 * 
	 * @param appointmentDTO
	 * @return
	 * @throws PatientNotExistsException
	 * @throws DoctorNotFoundException
	 */
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.add.appointment")
	public ResponseEntity<Message> addAppointment(@RequestBody AppointmentDTO appointmentDTO)
			throws PatientNotExistsException, DoctorNotFoundException {
		logger.info("This add appointment method had call to appointment service");
		return ResponseEntity.ok(appointmentService.addAppointment(appointmentDTO));

	}

	/**
	 * this method will list all appointment of patient
	 * 
	 * @param patientId
	 * @return
	 * @throws PatientAppointmentNotFound
	 * @throws PatientNotExistsException
	 */
	@GetMapping(path = "/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.list.patient.appointment")
	public ResponseEntity<List<Appointment>> listAllAppointmentOfPatient(@PathVariable("patientId") Integer patientId)
			throws PatientAppointmentNotFound, PatientNotExistsException {
		logger.info("This list all appointment of patient method had call to patient appointment service");
		return ResponseEntity.ok(appointmentService.listAllAppointmentOfPatient(patientId));

	}

	/**
	 * this method will list all appointment of doctor
	 * 
	 * @param doctorId
	 * @param date
	 * @return
	 * @throws DoctorAppointmentNotFount
	 * @throws DoctorNotFoundException
	 */
	@GetMapping(path = "/{doctorId}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.list.doctor.appointment")
	public ResponseEntity<List<Appointment>> listAllAppointmentOfDoctor(@PathVariable("doctorId") Integer doctorId,
			@PathVariable("date") String date) throws DoctorAppointmentNotFound, DoctorNotFoundException {
		logger.info("This list all appointment of doctor method had call to patient appointment service");
		return ResponseEntity.ok(appointmentService.appointmentOfDoctorOnDate(doctorId, date));

	}

}
