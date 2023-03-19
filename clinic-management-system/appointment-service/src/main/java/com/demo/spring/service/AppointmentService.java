package com.demo.spring.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.dto.AppointmentDTO;
import com.demo.spring.dto.DoctorDTO;
import com.demo.spring.dto.PatientDTO;
import com.demo.spring.entity.Appointment;
import com.demo.spring.exception.DoctorAppointmentNotFound;
import com.demo.spring.exception.DoctorNotFoundException;
import com.demo.spring.exception.PatientAppointmentNotFound;
import com.demo.spring.exception.PatientNotExistsException;
import com.demo.spring.repository.AppointmentRepository;
import com.demo.spring.util.Message;
import com.demo.spring.util.ServerConfiguration;

@Service
@EnableConfigurationProperties(ServerConfiguration.class)
public class AppointmentService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ServerConfiguration server;

	/**
	 * this method will add an appointment
	 * 
	 * @param appointmentDTO
	 * @return
	 * @throws DoctorNotFoundException
	 * @throws PatientNotExistsException
	 */
	public Message addAppointment(AppointmentDTO appointmentDTO)
			throws DoctorNotFoundException, PatientNotExistsException {
		PatientDTO patientDTO = restTemplate.getForEntity(server.getPatientServer() + "/patient/{patientId}",
				PatientDTO.class, appointmentDTO.getPatientId()).getBody();
		if (patientDTO != null && patientDTO.getPatientId() == appointmentDTO.getPatientId()) {
			DoctorDTO doctorDTO = restTemplate.getForEntity(server.getClinicServer() + "/clinic/doctor/find/{doctorId}",
					DoctorDTO.class, appointmentDTO.getDoctorId()).getBody();
			if (doctorDTO != null && doctorDTO.getDoctorId() == appointmentDTO.getDoctorId()) {
				Appointment appointment = new Appointment(appointmentDTO.getDoctorId(), appointmentDTO.getPatientId(),
						appointmentDTO.getDate());
				appointmentRepository.save(appointment);
				logger.info("Appointment booked for patient with Id {}",appointmentDTO.getPatientId());
				return new Message("Appointment Booked..");
			} else {
				logger.error("Exception : Doctor Not Found Exception Thrown");
				throw new DoctorNotFoundException();
			}
		} else {
			logger.error("Exception : Patient Not Exists Exception Thrown");
			throw new PatientNotExistsException();
		}
	}

	/**
	 * this method will list all appointment of patient
	 * 
	 * @param patientId
	 * @return
	 * @throws PatientAppointmentNotFound
	 * @throws PatientNotExistsException
	 */
	public List<Appointment> listAllAppointmentOfPatient(Integer patientId)
			throws PatientAppointmentNotFound, PatientNotExistsException {
		PatientDTO patientDTO = restTemplate
				.getForEntity(server.getPatientServer() + "/patient/{patientId}", PatientDTO.class, patientId)
				.getBody();
		if (patientDTO != null && patientDTO.getPatientId() == patientId) {
			List<Appointment> listAppointment = appointmentRepository.findAllByPatientId(patientId);
			if (listAppointment.isEmpty()) {
				logger.error("Exception : Patient Appointment Not Found Thrown");
				throw new PatientAppointmentNotFound();
			} else {
				logger.info("Returned all appoinement of patient with Id {}",patientId);
				return listAppointment;
			}
		} else {
			logger.error("Exception : Patient Not Exists Exception Thrown");
			throw new PatientNotExistsException();
		}
	}

	/**
	 * this method will list all appointment of doctor on date
	 * 
	 * @param doctorId
	 * @param date
	 * @return
	 * @throws DoctorAppointmentNotFount
	 * @throws DoctorNotFoundException
	 */
	public List<Appointment> appointmentOfDoctorOnDate(Integer doctorId, String date)
			throws DoctorAppointmentNotFound, DoctorNotFoundException {

		DoctorDTO doctorDTO = restTemplate
				.getForEntity(server.getClinicServer() + "/clinic/doctor/find/{doctorId}", DoctorDTO.class, doctorId)
				.getBody();
		if (doctorDTO != null && doctorDTO.getDoctorId() == doctorId) {
			List<Appointment> listAppointment = appointmentRepository.findAllByDoctorIdAndDate(doctorId, date);
			if (listAppointment.isEmpty()) {
				logger.error("Exception : Doctor Appointment Not Found Exception Thrown");
				throw new DoctorAppointmentNotFound();
			} else {
				logger.info("Returned all appoinement of doctor with Id {}",doctorId);
				return listAppointment;
			}
		} else {
			logger.error("Exception : Doctor Not Found Exception Thrown");
			throw new DoctorNotFoundException();
		}
	}

}
