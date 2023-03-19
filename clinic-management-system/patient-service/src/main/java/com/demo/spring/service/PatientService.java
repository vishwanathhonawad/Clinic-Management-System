package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dto.PatientDTO;
import com.demo.spring.entity.Patient;
import com.demo.spring.exception.EmailExistsException;
import com.demo.spring.exception.NoPatientExistsByNameException;
import com.demo.spring.exception.PatientExistsException;
import com.demo.spring.exception.PatientNotExistsException;
import com.demo.spring.repository.PatientRepository;
import com.demo.spring.util.Message;

@Service
public class PatientService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	PatientRepository patientRepository;

	/**
	 * this method will register patient
	 * 
	 * @param patientDTO
	 * @return
	 * @throws PatientExistsException
	 */
	public Message registerPatient(PatientDTO patientDTO) throws PatientExistsException {
		List<Patient> listPatient = patientRepository.findAllByEmail(patientDTO.getEmail());
		if (listPatient.isEmpty()) {
			Patient patient = new Patient(patientDTO.getFirstName(), patientDTO.getLastName(), patientDTO.getEmail());
			patientRepository.save(patient);
			logger.info("Patient Registered Successfully with Email {}",patientDTO.getEmail());
			return new Message("Patient Saved Successfully");
		} else {
			logger.error("Exception : Patient Alredy Exists");
			throw new PatientExistsException();

		}
	}

	/**
	 * this method will return patient object if exists by id
	 * 
	 * @param patientId
	 * @return
	 * @throws PatientNotExistsException
	 */
	public Patient findPatientById(int patientId) throws PatientNotExistsException {
		Optional<Patient> patientOp = patientRepository.findById(patientId);
		if (patientOp.isPresent()) {
			return patientOp.get();
		} else {
			throw new PatientNotExistsException();

		}
	}

	/**
	 * this method will return list of patient object with same first name
	 * 
	 * @param patientName
	 * @return
	 * @throws NoPatientExistsByNameException
	 */
	public List<Patient> findAllPatientByName(String patientName) throws NoPatientExistsByNameException {
		List<Patient> patientList = patientRepository.findAllByFirstName(patientName);
		if (patientList.isEmpty()) {
			throw new NoPatientExistsByNameException();
		} else {
			return patientList;
		}
	}

	/**
	 * this method will update patient details
	 * 
	 * @param patientDTO
	 * @return
	 * @throws PatientNotExistsException
	 * @throws EmailExistsException
	 */
	public Message updatePatient(PatientDTO patientDTO) throws PatientNotExistsException, EmailExistsException {
		if (patientRepository.existsById(patientDTO.getPatientId())) {
			
			if (patientRepository.findAllByEmailNotId(patientDTO.getPatientId(), patientDTO.getEmail()).isEmpty()) {
				patientRepository.updatePatient(patientDTO.getPatientId(), patientDTO.getFirstName(),
						patientDTO.getLastName(), patientDTO.getEmail());
				return new Message("Patient updated successfully..");
			} else {
				throw new EmailExistsException();
			}

		} else {
			throw new PatientNotExistsException();
		}
	}

}
